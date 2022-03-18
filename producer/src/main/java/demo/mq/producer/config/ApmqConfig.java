package demo.mq.producer.config;

import demo.mq.producer.listener.MessageSendingContextListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextListener;

@Configuration
public class ApmqConfig {

    @Value("${mq.topic-exchange-name}")
    private String topicExchangeName;

    @Value("${mq.queue-name}")
    private String queueName;

    @Value("${mq.routing-key-base}")
    private String routingKeyBase;

    @Autowired
    private MessageSendingContextListener messageSendingContextListener;

    @Bean
    ServletListenerRegistrationBean<ServletContextListener> servletListener() {
        ServletListenerRegistrationBean<ServletContextListener> srb	= new ServletListenerRegistrationBean<>();
        srb.setListener(messageSendingContextListener);
        return srb;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKeyBase + ".#");
    }
}

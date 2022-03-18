package demo.rabbitmq.producer;

import demo.rabbitmq.producer.listener.RabbitMqServletContextListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContextListener;

@SpringBootApplication
public class ProducerApplication {

	@Value("${rabbitmq.topic-exchange-name}")
	private String topicExchangeName;

	@Value("${rabbitmq.queue-name}")
	private String queueName;

	@Value("${rabbitmq.routing-key-base}")
	private String routingKeyBase;

	@Autowired
	private RabbitMqServletContextListener rabbitMqListener;

	@Bean
	ServletListenerRegistrationBean<ServletContextListener> servletListener() {
		ServletListenerRegistrationBean<ServletContextListener> srb	= new ServletListenerRegistrationBean<>();
		srb.setListener(rabbitMqListener);
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

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

}

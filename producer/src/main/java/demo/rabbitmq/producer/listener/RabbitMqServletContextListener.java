package demo.rabbitmq.producer.listener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalDateTime;

@Component
public class RabbitMqServletContextListener implements ServletContextListener, DisposableBean {
    @Value("${rabbitmq.topic-exchange-name}")
    private String topicExchangeName;

    @Value("${rabbitmq.routing-key-base}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        rabbitTemplate.convertAndSend(topicExchangeName, routingKey,
                "Producer started at " + LocalDateTime.now());
    }

    @Override
    public void destroy() {
        rabbitTemplate.convertAndSend(topicExchangeName, routingKey,
                "Producer shut down at " + LocalDateTime.now());
    }
}

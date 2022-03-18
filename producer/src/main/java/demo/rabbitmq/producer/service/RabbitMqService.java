package demo.rabbitmq.producer.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService {
    @Value("${rabbitmq.topic-exchange-name}")
    private String topicExchangeName;

    @Value("${rabbitmq.routing-key-base}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(topicExchangeName, routingKey, message);
    }
}
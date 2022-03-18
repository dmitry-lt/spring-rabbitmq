package demo.mq.producer.service.rabbitmq;

import demo.mq.producer.service.MessageSendingService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMqService implements MessageSendingService {
    @Value("${mq.topic-exchange-name}")
    private String topicExchangeName;

    @Value("${mq.routing-key-base}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String message) {
        log.info("Sending message: " + message);
        rabbitTemplate.convertAndSend(topicExchangeName, routingKey, message);
    }
}

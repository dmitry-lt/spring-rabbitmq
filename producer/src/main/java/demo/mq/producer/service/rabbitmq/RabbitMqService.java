package demo.mq.producer.service.rabbitmq;

import demo.mq.producer.config.AmqpProperties;
import demo.mq.producer.service.MessageSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMqService implements MessageSendingService {
    private final AmqpProperties amqpProperties;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String message) {
        log.info("Sending message: " + message);
        rabbitTemplate.convertAndSend(amqpProperties.getTopicExchangeName(), amqpProperties.getRoutingKeyBase(), message);
    }
}

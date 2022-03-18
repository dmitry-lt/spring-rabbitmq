package demo.mq.producer.integration;

import demo.mq.producer.config.AmqpProperties;
import demo.mq.producer.service.rabbitmq.RabbitMqService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RabbitMqServiceIntegrationTests {
    private final String queueName = "producerIntegrationTestQueue-" + System.currentTimeMillis();

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    TopicExchange topicExchange;

    @Autowired
    AmqpProperties amqpProperties;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    RabbitMqService rabbitMqService;

    @Test
    public void testSendMessageToTemporaryQueue() throws InterruptedException {
        // set up
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);

        // the server will delete the queue when it is no longer in use
        var queue = new Queue(queueName, false, true, true);
        admin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(amqpProperties.getRoutingKeyBase() + ".#");
        admin.declareBinding(binding);

        // test
        var message = "integration test message " + LocalDateTime.now();
        rabbitMqService.sendMessage(message);

        var receivedMessage = amqpTemplate.receive(queueName, 10000);

        assertNotNull(receivedMessage);
        assertEquals(message, new String(receivedMessage.getBody()));
    }

}

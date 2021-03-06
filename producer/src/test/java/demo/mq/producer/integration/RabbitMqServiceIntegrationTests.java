package demo.mq.producer.integration;

import demo.mq.producer.config.AmqpProperties;
import demo.mq.producer.service.rabbitmq.RabbitMqService;
import org.junit.jupiter.api.BeforeEach;
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
    private final String queueName = "producer-integration-test-queue-" + System.currentTimeMillis();

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

    @BeforeEach
    void init() {
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);

        // the server will delete the queue when it is no longer in use
        var queue = new Queue(queueName, false, true, true);
        admin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(amqpProperties.getRoutingKeyBase() + ".#");
        admin.declareBinding(binding);
    }

    @Test
    public void testSendMessageToTemporaryQueue() throws InterruptedException {
        // This integration test verifies that 
        // rabbitMqService.sendMessage(message) does send the message 
        // to the configured RabbitMQ exchange with the configured routing key.
        // Before running the test, we create a temporary queue that should receive the message
        
        var message = "integration test message " + LocalDateTime.now();
        rabbitMqService.sendMessage(message);

        var receivedMessage = amqpTemplate.receive(queueName, 10000);

        assertNotNull(receivedMessage);
        assertEquals(message, new String(receivedMessage.getBody()));
    }

}

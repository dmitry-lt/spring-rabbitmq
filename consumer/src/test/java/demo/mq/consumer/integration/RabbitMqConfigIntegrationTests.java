package demo.mq.consumer.integration;

import demo.mq.consumer.config.AmqpProperties;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootTest
public class RabbitMqConfigIntegrationTests {
    private static final String exchangeName = "consumer-integration-test-exchange-" + System.currentTimeMillis();
    private static final String queueName = "consumer-integration-test-queue-" + System.currentTimeMillis();

    @TestConfiguration
    static class ContextConfiguration {
        @Bean
        public AmqpProperties amqpPropertiesTest() {
            return new AmqpProperties() {
                @Override
                public String getQueueName() {
                    return RabbitMqConfigIntegrationTests.queueName;
                }
            };
        }

        @Bean
        Queue queue() {
            return new Queue(queueName, false, true, true);
        }

        @Bean
        DirectExchange exchange() {
            return new DirectExchange(exchangeName, false, true);
        }

        @Bean
        Binding binding(Queue queue, DirectExchange exchange) {
            return BindingBuilder.bind(queue).to(exchange).withQueueName();
        }
    }

    @Autowired
    AmqpTemplate amqpTemplate;

    @Test
    public void testSendMessageToTemporaryExchange() {
        var message = "integration test message " + LocalDateTime.now();

        amqpTemplate.convertAndSend(exchangeName, queueName, message);

//        assertNotNull(receivedMessage);
//        assertEquals(message, new String(receivedMessage.getBody()));
    }

}
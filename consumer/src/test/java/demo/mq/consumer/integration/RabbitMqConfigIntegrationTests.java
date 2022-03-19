package demo.mq.consumer.integration;

import demo.mq.consumer.config.AmqpProperties;
import demo.mq.consumer.receiver.MessageReceiver;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class RabbitMqConfigIntegrationTests {
    private static final String exchangeName = "consumer-integration-test-exchange-" + System.currentTimeMillis();
    private static final String queueName = "consumer-integration-test-queue-" + System.currentTimeMillis();

    @TestConfiguration
    static class ContextConfiguration {
        @Bean
        public AmqpProperties amqpProperties() {
            var propertiesMock = mock(AmqpProperties.class);
            when(propertiesMock.getQueueName()).thenReturn(queueName);
            return propertiesMock;
        }

        @Bean
        public MessageReceiver messageReceiver() {
            return mock(MessageReceiver.class);
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

    @Autowired
    MessageReceiver messageReceiverMock;

    @Test
    public void testSendMessageToTemporaryExchange() {
        var message = "integration test message " + LocalDateTime.now();

        amqpTemplate.convertAndSend(exchangeName, queueName, message);

        verify(messageReceiverMock, timeout(10000)).handleMessage(message);
    }

}
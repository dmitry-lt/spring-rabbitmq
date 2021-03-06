package demo.mq.producer.unit;

import demo.mq.producer.config.AmqpProperties;
import demo.mq.producer.service.rabbitmq.RabbitMqService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RabbitMqServiceUnitTests {
    @Test
    public void givenRabbitMqService_whenSendMessage_thenConvertAndSend() {
        // this unit test verifies that 
        // rabbitMqService.sendMessage(message) does send the message 
        // to the configured RabbitMQ exchange with the configured routing key
        
        // given
        var topicExchangeName = "exchange";
        var routingKeyBase = "routing.key";
        var message = "test message";

        var propertiesMock = mock(AmqpProperties.class);
        when(propertiesMock.getTopicExchangeName()).thenReturn(topicExchangeName);
        when(propertiesMock.getRoutingKeyBase()).thenReturn(routingKeyBase);

        var rabbitTemplateMock = mock(RabbitTemplate.class);

        var rabbitMqService = new RabbitMqService(propertiesMock, rabbitTemplateMock);

        // when
        rabbitMqService.sendMessage(message);

        // then
        verify(rabbitTemplateMock).convertAndSend(topicExchangeName, routingKeyBase, message);
    }
}

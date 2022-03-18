package demo.mq.producer.unit;

import demo.mq.producer.config.AmqpProperties;
import demo.mq.producer.service.rabbitmq.RabbitMqService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMqServiceUnitTests {
    @Test
    public void givenRabbitMqService_whenSendMessage_thenConvertAndSend() {
        // given
        var topicExchangeName = "exchange";
        var routingKeyBase = "routing.key";
        var message = "test message";

        var propertiesMock = Mockito.mock(AmqpProperties.class);
        Mockito.when(propertiesMock.getTopicExchangeName()).thenReturn(topicExchangeName);
        Mockito.when(propertiesMock.getRoutingKeyBase()).thenReturn(routingKeyBase);

        var rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);

        var rabbitMqService = new RabbitMqService(propertiesMock, rabbitTemplateMock);

        // when
        rabbitMqService.sendMessage(message);

        // then
        Mockito.verify(rabbitTemplateMock).convertAndSend(topicExchangeName, routingKeyBase, message);
    }
}

package demo.mq.producer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AmqpProperties {
    @Value("${mq.topic-exchange-name}")
    private String topicExchangeName;

    @Value("${mq.queue-name}")
    private String queueName;

    @Value("${mq.routing-key-base}")
    private String routingKeyBase;
}

package demo.mq.consumer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AmqpProperties {
    @Value("${mq.queue-name}")
    private String queueName;
}

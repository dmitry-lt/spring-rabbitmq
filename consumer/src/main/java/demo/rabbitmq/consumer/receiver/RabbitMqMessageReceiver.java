package demo.rabbitmq.consumer.receiver;

import org.springframework.stereotype.Component;

@Component
public class RabbitMqMessageReceiver {
    public void receiveMessage(String message) {
        System.out.println("Message received: " + message);
    }
}

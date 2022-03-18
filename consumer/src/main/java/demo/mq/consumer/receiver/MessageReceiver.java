package demo.mq.consumer.receiver;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageReceiver {
    public void receiveMessage(String message) {
        log.info("Message received: " + message);
    }
}

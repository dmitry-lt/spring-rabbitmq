package demo.mq.consumer.receiver.impl;

import demo.mq.consumer.receiver.MessageReceiver;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

@Component("messageReceiver")
@Slf4j
public class LoggingMessageReceiver implements MessageReceiver {
    @Override
    public void handleMessage(String text) {
        log.info("Message received: " + text);
    }

    @Override
    public void handleMessage(Map map) {
        String text = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"))
                .toString();
        handleMessage(text);
    }

    @Override
    public void handleMessage(byte[] bytes) {
        String text = new String(bytes);
        handleMessage(text);
    }

    @Override
    public void handleMessage(Serializable obj) {
        handleMessage(obj.toString());
    }
}

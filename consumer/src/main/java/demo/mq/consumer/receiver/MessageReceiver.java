package demo.mq.consumer.receiver;

import java.io.Serializable;
import java.util.Map;

public interface MessageReceiver {
    void handleMessage(String text);

    void handleMessage(Map map);

    void handleMessage(byte[] bytes);

    void handleMessage(Serializable obj);
}

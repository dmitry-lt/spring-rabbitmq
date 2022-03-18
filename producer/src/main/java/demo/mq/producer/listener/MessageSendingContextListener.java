package demo.mq.producer.listener;

import demo.mq.producer.service.MessageSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalDateTime;

@Component
public class MessageSendingContextListener implements ServletContextListener {

    @Autowired
    private MessageSendingService messageSendingService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        messageSendingService.sendMessage("Producer started at " + LocalDateTime.now());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        messageSendingService.sendMessage("Producer shut down at " + LocalDateTime.now());
    }
}

package demo.rabbitmq.producer.listener;

import demo.rabbitmq.producer.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalDateTime;

@Component
public class RabbitMqServletContextListener implements ServletContextListener {

    @Autowired
    private RabbitMqService rabbitMqService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        rabbitMqService.sendMessage("Producer started at " + LocalDateTime.now());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        rabbitMqService.sendMessage("Producer shut down at " + LocalDateTime.now());
    }
}

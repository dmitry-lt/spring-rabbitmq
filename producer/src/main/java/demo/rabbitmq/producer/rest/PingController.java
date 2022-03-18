package demo.rabbitmq.producer.rest;

import demo.rabbitmq.producer.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PingController {

    @Autowired
    private RabbitMqService rabbitMqService;

    @RequestMapping("/ping")
    public void ping() {
        rabbitMqService.sendMessage("Ping received at " + LocalDateTime.now());
    }
}

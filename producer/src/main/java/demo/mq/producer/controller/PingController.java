package demo.mq.producer.controller;

import demo.mq.producer.service.MessageSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PingController {

    @Autowired
    private MessageSendingService messageSendingService;

    @RequestMapping("/ping")
    public void ping() {
        messageSendingService.sendMessage("Ping received at " + LocalDateTime.now());
    }
}

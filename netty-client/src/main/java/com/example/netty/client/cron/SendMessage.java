package com.example.netty.client.cron;

import com.example.netty.client.service.EchoClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by jessie on 17/8/19.
 */
@Service
public class SendMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessage.class);

    @Scheduled(cron = "0/10 * * * * ?")
    public void sendMessage() {
        LOGGER.debug("发送消息");
        EchoClientService client = new EchoClientService("127.0.0.1", 8080);
        try {
            client.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

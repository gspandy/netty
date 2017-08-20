package com.example.netty.server.listener;

import com.example.netty.server.service.EchoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by jessie on 17/8/18.
 */
public class ApplicationEventListener implements ApplicationListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        // 在这里可以监听到Spring Boot的生命周期
        if (applicationEvent instanceof ContextRefreshedEvent) {
            EchoService bean = new EchoService();
            try {
                bean.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("启动 netty server...");
        }

    }
}

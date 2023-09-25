package com.szmengran.authorization;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring Boot Starter
 *
 * @author Frank Zhang
 */
@EnableAsync
@EnableDubbo
@EnableFeignClients(basePackages = {"com.szmengran.authorization.infrastructure.wechat.client", "com.szmengran.authorization.infrastructure.oauth2.client"})
@SpringBootApplication(scanBasePackages = {"com.szmengran.authorization", "com.alibaba.cola"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}




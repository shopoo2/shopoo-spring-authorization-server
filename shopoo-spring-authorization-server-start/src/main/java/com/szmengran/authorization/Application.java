package com.szmengran.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Spring Boot Starter
 *
 * @author Frank Zhang
 */
@EnableFeignClients(basePackages = {"com.szmengran.authorization.infrastructure.wechat.client"})
@SpringBootApplication(scanBasePackages = {"com.szmengran.authorization", "com.alibaba.cola"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

package com.szmengran.authorization;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Spring Boot Starter
 *
 * @author Frank Zhang
 */
@EnableDubbo
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.szmengran.authorization.infrastructure.wechat.client"})
@SpringBootApplication(scanBasePackages = {"com.szmengran.authorization", "com.alibaba.cola"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}




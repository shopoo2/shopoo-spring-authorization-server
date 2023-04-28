//package com.szmengran.authorization.infrastructure.wechat.client;
//
//import feign.Logger;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author MaoYuan.Li
// * @Date 2023/3/21 19:11
// * @Version 1.0
// */
//@Configuration
//public class FeignClientConfiguration implements RequestInterceptor {
//
//    @Bean
//    Logger.Level feignLoggerLevel() {
//        //这里记录所有，根据实际情况选择合适的日志level
//        return Logger.Level.FULL;
//    }
//
//    @Override
//    public void apply(final RequestTemplate requestTemplate) {
//    }
//}

package com.szmengran.authorization.infrastructure.oauth2.config;

import feign.Logger;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author MaoYuan.Li
 * @Date 2023/3/21 19:11
 * @Version 1.0
 */
@Slf4j
@Configuration
public class Oauth2FeignClientConfiguration {

	@Bean
	Logger.Level feignLogger() {
		if (log.isDebugEnabled()) {
			return Logger.Level.FULL;
		}
		return Logger.Level.NONE;
	}
}

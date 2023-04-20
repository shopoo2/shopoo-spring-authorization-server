package com.szmengran.authorization.domain.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 16:58
 * @Version 1.0
 */
@Data
@Configuration
@ConfigurationProperties("wechat")
public class WechatProperties {

    private MiniProgram miniProgram;
    
    @Data
    public static class MiniProgram {
        private Map<String, String> map = new HashMap<>();
    }
    
}

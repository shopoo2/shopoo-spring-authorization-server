package com.szmengran.authorization.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 16:40
 * @Version 1.0
 */
@Data
public class WechatMiniProgramCO {
    
    private String openid;
    @JsonProperty
    private String sessionKey;
    private String unionid;
    
}

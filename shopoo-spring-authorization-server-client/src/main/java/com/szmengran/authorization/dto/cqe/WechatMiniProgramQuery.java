package com.szmengran.authorization.dto.cqe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.szmengran.cola.dto.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 16:52
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WechatMiniProgramQuery extends Query {
    
    private String appId;
    
    private String secret;
    
    @JsonProperty("js_code")
    private String jsCode;
    
    @JsonProperty("grant_type")
    private String grantType;
    
}

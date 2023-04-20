package com.szmengran.authorization.web;

import com.szmengran.authorization.dto.WechatMiniProgramCO;
import com.szmengran.authorization.dto.cqe.WechatMiniProgramQuery;
import com.szmengran.cola.dto.SingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 18:07
 * @Version 1.0
 */
@Slf4j
@RestController("/oauth2")
public class WechatMiniProgramController {
    
    @GetMapping("/miniprogram/login")
    public SingleResponse<WechatMiniProgramCO> login(WechatMiniProgramQuery wechatMiniProgramQuery) {
        log.info("miniprogram login {}", wechatMiniProgramQuery);
        return null;
    }
}

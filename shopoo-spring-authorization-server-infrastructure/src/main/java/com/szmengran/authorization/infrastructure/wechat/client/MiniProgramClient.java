package com.szmengran.authorization.infrastructure.wechat.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 微信小程序对接接口
 *
 * @Author MaoYuan.Li
 * @Date 2023/4/19 14:54
 * @Version 1.0
 */
@FeignClient(value = "wechat-mini-program", url = "https://api.weixin.qq.com", configuration = FeignClientConfiguration.class)
public interface MiniProgramClient {
    
    /**
     * 微信小程序登录信息
     * @param appId
     * @param appSecret
     * @param code
     * @return
     * @throws Exception
     * @author <a href="mailto:android_li@sina.cn">Joe</a>
     */
    @GetMapping(value="/sns/jscode2session?appid={appId}&secret={appSecret}&js_code={code}&grant_type=authorization_code", headers="Content-Type=application/json;charset=utf-8" , produces = "application/json; charset=UTF-8")
    String getLoginInfo(@PathVariable("appId") String appId, @PathVariable("appSecret") String appSecret, @PathVariable("code") String code);
    
}

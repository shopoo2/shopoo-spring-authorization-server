//package com.szmengran.authorization.infrastructure.wechat.client;
//
//import com.szmengran.authorization.dto.WechatMiniProgramCO;
//import feign.Headers;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
///**
// * 微信小程序对接接口
// *
// * @Author MaoYuan.Li
// * @Date 2023/4/19 14:54
// * @Version 1.0
// */
//@FeignClient(value = "wechat-mini-program", url = "https://api.weixin.qq.com", configuration = FeignClientConfiguration.class)
//public interface MiniProgramClient {
//
//    /**
//     * @description: 获取微信token
//     * @param appId
//     * @param secret
//     * @param code
//     * @return: java.lang.String
//     * @author MaoYuan.Li
//     * @date: 2023/4/20 11:48
//     */
//    @GetMapping(value="/sns/oauth2/access_token?appid={appId}&secret={secret}&code={code}&grant_type=authorization_code")
//    String getToken(@PathVariable("appId") String appId, @PathVariable("secret") String secret, @PathVariable("code") String code);
//
//    /**
//     * @description: 微信通过授权码获取用户信息
//     * @param accessToken
//     * @param openId
//     * @return: java.lang.String
//     * @author MaoYuan.Li
//     * @date: 2023/4/20 11:48
//     */
//    @GetMapping(value="/sns/userinfo?access_token={accessToken}&openid={openId}OPENID&lang=zh_CN", headers="Content-Type=application/json;charset=utf-8", consumes="application/json; charset=UTF-8")
//    String getOpenInfo(@PathVariable("accessToken") String accessToken, @PathVariable("openId") String openId);
//
//    /**
//     * @description: 微信小程序登录信息
//     * @param appid
//     * @param secret
//     * @param jsCode
//     * @return: com.szmengran.authorization.dto.WechatMiniProgramCO
//     * @author MaoYuan.Li
//     * @date: 2023/4/20 11:48
//     */
//    @GetMapping(value = "/sns/jscode2session?appid={appid}&secret={secret}&js_code={jsCode}&grant_type=authorization_code")
//    @Headers("Content-Type: application/json;charset=UTF-8")
//    String jscode2session(@PathVariable("appid") String appid, @PathVariable("secret") String secret, @PathVariable("jsCode") String jsCode);
//}

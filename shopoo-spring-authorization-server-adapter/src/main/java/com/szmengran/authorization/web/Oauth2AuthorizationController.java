package com.szmengran.authorization.web;


import java.security.Principal;

import com.shopoo.wechat.api.WechatFacade;
import com.shopoo.wechat.dto.cqe.LoginCmd;
import com.szmengran.cola.dto.SingleResponse;
import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author maoyuan.li
 * @since 2023-01-13
 */
@RestController
@RequestMapping("/oauth2")
public class Oauth2AuthorizationController {

    @DubboReference
    private WechatFacade wechatFacade;

    @GetMapping("/info")
    public SingleResponse<Object> info() {
        LoginCmd loginCmd = LoginCmd.builder().appId("1").appSecret("2").build();
        wechatFacade.getLoginInfo(loginCmd);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return SingleResponse.of(authentication.getPrincipal());
    }
    
    @GetMapping("/login")
    public SingleResponse<String> login(Principal principal) {
        return SingleResponse.of(principal.getName());
    }
    
    @GetMapping("/test")
    public SingleResponse<String> login() {
        return SingleResponse.of("Hello World!");
    }
}

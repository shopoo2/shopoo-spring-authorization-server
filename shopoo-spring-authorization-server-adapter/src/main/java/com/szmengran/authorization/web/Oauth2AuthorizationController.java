package com.szmengran.authorization.web;


import com.szmengran.cola.dto.SingleResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author maoyuan.li
 * @since 2023-01-13
 */
@Controller
@RequestMapping("/oauth2")
public class Oauth2AuthorizationController {

    @GetMapping("/info")
    public SingleResponse<Object> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return SingleResponse.of(authentication.getPrincipal());
    }
    
    @GetMapping("/login")
    public String login(Principal principal) {
        System.out.println(principal.getName());
        return "login";
    }
}

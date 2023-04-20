package com.szmengran.authorization.domain.wechat.miniprogram;

import com.szmengran.authorization.domain.utils.OAuth2EndpointUtils;
import com.szmengran.authorization.domain.utils.Constants;
import com.szmengran.authorization.domain.utils.WechatMiniProgramConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 18:37
 * @Version 1.0
 */
public class MiniProgramAuthorizationConverter implements AuthenticationConverter {
    
    @Override
    public Authentication convert(final HttpServletRequest request) {
        String grantType = request.getParameter(Constants.GRANT_TYPE_KEY);
        if (!MiniProgramAuthorizationToken.GRANT_TYPE.getValue().equals(grantType)) {
            return null;
        }
        String code = request.getParameter(WechatMiniProgramConstants.JS_CODE);
        String appid = request.getParameter(WechatMiniProgramConstants.APPID);
    
        if (!StringUtils.hasText(code)) {
            throw new BadCredentialsException("Invalid wechat authorization_code");
        }
    
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
        String scope = parameters.getFirst(Constants.SCOPE_KEY);
        if (StringUtils.hasText(scope) && (parameters.get(Constants.SCOPE_KEY)).size() != 1) {
            OAuth2EndpointUtils.throwError("invalid_request", Constants.SCOPE_KEY, "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2");
        }
    
        Set<String> scopes = null;
        if (StringUtils.hasText(scope)) {
            scopes = new HashSet(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }
    
        Map<String, Object> additionalParameters = new HashMap();
        parameters.forEach((key, value) -> {
            if (!key.equals(WechatMiniProgramConstants.JS_CODE) && !key.equals(Constants.SCOPE_KEY) && !key.equals(WechatMiniProgramConstants.APPID)) {
                additionalParameters.put(key, value.get(0));
            }
        
        });
    
        return new MiniProgramAuthorizationToken(appid, code, scopes, additionalParameters);
    }
}

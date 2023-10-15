package com.szmengran.authorization.domain.wechat.web;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;
import org.springframework.util.Assert;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 18:30
 * @Version 1.0
 */
public class WebAuthorizationToken extends AbstractAuthenticationToken {
    
    private static final long serialVersionUID;
    
    static {
        serialVersionUID = SpringAuthorizationServerVersion.SERIAL_VERSION_UID;
    }
    
    public static final AuthorizationGrantType GRANT_TYPE = new AuthorizationGrantType("wechat_web");
    
    /**
     * @see <a href=
     * "https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">登录
     * - code2Session</a>
     */
    @Getter
    private final String code;
    
    @Getter
    private final Authentication clientPrincipal;
    
    @Getter
    private final Set<String> scopes;
    private final Map<String, Object> additionalParameters;
    
    public WebAuthorizationToken(Authentication clientPrincipal, String code, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
        Assert.notNull(code, "wechat authorization_code cannot be null");
        Assert.notNull(additionalParameters, "additionalParameters cannot be null");
        this.clientPrincipal = clientPrincipal;
        this.code = code;
        this.scopes = scopes;
        this.additionalParameters = additionalParameters;
    }
    
    public Map<String, Object> getAdditionalParameters() {
        return this.additionalParameters;
    }
    
    @Override
    public Object getCredentials() {
        return null;
    }
    
    @Override
    public Object getPrincipal() {
        return this.clientPrincipal;
    }
}

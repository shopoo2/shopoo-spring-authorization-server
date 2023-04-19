package com.szmengran.authorization.domain.password;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @Author MaoYuan.Li
 * @Date 2023/3/8 18:47
 * @Version 1.0
 */
public class CustomerUsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID;
    
    @Getter
    private final Authentication clientPrincipal;
    
    @Getter
    private final Set<String> scopes;
    private final Map<String, Object> additionalParameters;
    
    public CustomerUsernamePasswordAuthenticationToken(Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
        Assert.notNull(additionalParameters, "additionalParameters cannot be null");
        this.clientPrincipal = clientPrincipal;
        this.scopes = scopes;
        this.additionalParameters = additionalParameters;
    }
    
    @Override
    public Object getPrincipal() {
        return this.clientPrincipal;
    }
    
    @Override
    public Object getCredentials() {
        return "";
    }
    
    public Map<String, Object> getAdditionalParameters() {
        return this.additionalParameters;
    }
    
    static {
        serialVersionUID = SpringAuthorizationServerVersion.SERIAL_VERSION_UID;
    }
}

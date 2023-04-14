package com.szmengran.authorization.domain.password;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author MaoYuan.Li
 * @Date 2023/3/8 18:47
 * @Version 1.0
 */
@Getter
public class OAuth2UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID;
    private final String clientId;
    private final Authentication principal;
    private final Set<String> scopes;
    private final Map<String, Object> additionalParameters;
    
    public OAuth2UsernamePasswordAuthenticationToken(String clientId, Authentication principal, @Nullable Set<String> scopes, @Nullable Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        Assert.hasText(clientId, "clientId cannot be empty");
        Assert.notNull(principal, "principal cannot be null");
        this.clientId = clientId;
        this.principal = principal;
        this.scopes = Collections.unmodifiableSet((Set)(scopes != null ? new HashSet(scopes) : Collections.emptySet()));
        this.additionalParameters = Collections.unmodifiableMap((Map)(additionalParameters != null ? new HashMap(additionalParameters) : Collections.emptyMap()));
    }
    
    public OAuth2UsernamePasswordAuthenticationToken(String clientId, Authentication principal, @Nullable Set<String> scopes) {
        super(Collections.emptyList());
        Assert.hasText(clientId, "clientId cannot be empty");
        Assert.notNull(principal, "principal cannot be null");
        this.clientId = clientId;
        this.principal = principal;
        this.scopes = Collections.unmodifiableSet((Set)(scopes != null ? new HashSet(scopes) : Collections.emptySet()));
        this.additionalParameters = Collections.emptyMap();
        this.setAuthenticated(true);
    }
    
    public Object getPrincipal() {
        return this.principal;
    }
    
    public Object getCredentials() {
        return "";
    }
    
    public String getClientId() {
        return this.clientId;
    }
    
    public Set<String> getScopes() {
        return this.scopes;
    }
    
    public Map<String, Object> getAdditionalParameters() {
        return this.additionalParameters;
    }
    
    static {
        serialVersionUID = SpringAuthorizationServerVersion.SERIAL_VERSION_UID;
    }

}

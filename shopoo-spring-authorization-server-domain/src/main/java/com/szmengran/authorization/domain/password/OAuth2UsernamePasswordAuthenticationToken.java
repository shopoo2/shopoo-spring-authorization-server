package com.szmengran.authorization.domain.password;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;

/**
 * @Author MaoYuan.Li
 * @Date 2023/3/8 18:47
 * @Version 1.0
 */
public class OAuth2UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID;
    private final RegisteredClient registeredClient;
    private final Authentication clientPrincipal;
    private final OAuth2AccessToken accessToken;
    private final Map<String, Object> additionalParameters;
    
    public OAuth2UsernamePasswordAuthenticationToken(RegisteredClient registeredClient, Authentication clientPrincipal, OAuth2AccessToken accessToken) {
        this(registeredClient, clientPrincipal, accessToken, Collections.emptyMap());
    }
    
    public OAuth2UsernamePasswordAuthenticationToken(RegisteredClient registeredClient, Authentication clientPrincipal, OAuth2AccessToken accessToken, Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
        Assert.notNull(accessToken, "accessToken cannot be null");
        Assert.notNull(additionalParameters, "additionalParameters cannot be null");
        this.registeredClient = registeredClient;
        this.clientPrincipal = clientPrincipal;
        this.accessToken = accessToken;
        this.additionalParameters = additionalParameters;
    }
    
    public Object getPrincipal() {
        return this.clientPrincipal;
    }
    
    public Object getCredentials() {
        return "";
    }
    
    public RegisteredClient getRegisteredClient() {
        return this.registeredClient;
    }
    
    public OAuth2AccessToken getAccessToken() {
        return this.accessToken;
    }
    
    public Map<String, Object> getAdditionalParameters() {
        return this.additionalParameters;
    }
    
    static {
        serialVersionUID = SpringAuthorizationServerVersion.SERIAL_VERSION_UID;
    }
}

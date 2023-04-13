package com.szmengran.authorization.domain.password;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author MaoYuan.Li
 * @Date 2023/3/8 18:47
 * @Version 1.0
 */
@Getter
public class OAuth2UsernamePasswordAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final Set<String> scopes;

    public OAuth2UsernamePasswordAuthenticationToken(Authentication clientPrincipal, @Nullable Set<String> scopes, @Nullable Map<String, Object> additionalParameters) {
        super(AuthorizationGrantType.PASSWORD, clientPrincipal, additionalParameters);
        this.scopes = Collections.unmodifiableSet((Set)(scopes != null ? new HashSet(scopes) : Collections.emptySet()));
    }

}

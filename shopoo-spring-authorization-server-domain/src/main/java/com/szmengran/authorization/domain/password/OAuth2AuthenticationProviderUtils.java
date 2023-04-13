package com.szmengran.authorization.domain.password;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;

/**
 * @Author MaoYuan.Li
 * @Date 2023/3/8 19:05
 * @Version 1.0
 */
public class OAuth2AuthenticationProviderUtils {

    private OAuth2AuthenticationProviderUtils() {
    }

    static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
        OAuth2ClientAuthenticationToken clientPrincipal = null;
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken)authentication.getPrincipal();
        }

        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        } else {
            throw new OAuth2AuthenticationException("invalid_client");
        }
    }

    static <T extends OAuth2Token> OAuth2Authorization invalidate(OAuth2Authorization authorization, T token) {
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.from(authorization).token(token, (metadata) -> {
            metadata.put(Token.INVALIDATED_METADATA_NAME, true);
        });
        if (OAuth2RefreshToken.class.isAssignableFrom(token.getClass())) {
            authorizationBuilder.token((OAuth2AccessToken)authorization.getAccessToken().getToken(), (metadata) -> {
                metadata.put(Token.INVALIDATED_METADATA_NAME, true);
            });
            Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
            if (authorizationCode != null && !authorizationCode.isInvalidated()) {
                authorizationBuilder.token((OAuth2AuthorizationCode)authorizationCode.getToken(), (metadata) -> {
                    metadata.put(Token.INVALIDATED_METADATA_NAME, true);
                });
            }
        }

        return authorizationBuilder.build();
    }
}
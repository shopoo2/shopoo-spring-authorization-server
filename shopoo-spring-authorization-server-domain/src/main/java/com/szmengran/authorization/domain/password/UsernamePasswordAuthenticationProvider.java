package com.szmengran.authorization.domain.password;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author MaoYuan.Li
 * @Date 2023/3/3 15:06
 * @Version 1.0
 */
@Slf4j
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public UsernamePasswordAuthenticationProvider(OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (OAuth2UsernamePasswordAuthenticationToken)authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(usernamePasswordAuthenticationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        if (log.isTraceEnabled()) {
            log.trace("Retrieved registered client");
        }

        if (!registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.CLIENT_CREDENTIALS)) {
            throw new OAuth2AuthenticationException("unauthorized_client");
        } else {
            Set<String> authorizedScopes = Collections.emptySet();
            if (!CollectionUtils.isEmpty(usernamePasswordAuthenticationToken.getScopes())) {
                Iterator var6 = usernamePasswordAuthenticationToken.getScopes().iterator();

                while(var6.hasNext()) {
                    String requestedScope = (String)var6.next();
                    if (!registeredClient.getScopes().contains(requestedScope)) {
                        throw new OAuth2AuthenticationException("invalid_scope");
                    }
                }

                authorizedScopes = new LinkedHashSet(usernamePasswordAuthenticationToken.getScopes());
            }

            if (log.isTraceEnabled()) {
                log.trace("Validated token request parameters");
            }

            OAuth2TokenContext tokenContext = ((DefaultOAuth2TokenContext.Builder)((DefaultOAuth2TokenContext.Builder)((DefaultOAuth2TokenContext.Builder)((DefaultOAuth2TokenContext.Builder)((DefaultOAuth2TokenContext.Builder)((DefaultOAuth2TokenContext.Builder)((DefaultOAuth2TokenContext.Builder)DefaultOAuth2TokenContext.builder().registeredClient(registeredClient)).principal(clientPrincipal)).authorizationServerContext(AuthorizationServerContextHolder.getContext())).authorizedScopes((Set)authorizedScopes)).tokenType(OAuth2TokenType.ACCESS_TOKEN)).authorizationGrantType(AuthorizationGrantType.PASSWORD)).authorizationGrant(usernamePasswordAuthenticationToken)).build();
            OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
            if (generatedAccessToken == null) {
                OAuth2Error error = new OAuth2Error("server_error", "The token generator failed to generate the access token.", "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2");
                throw new OAuth2AuthenticationException(error);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Generated access token");
                }

                OAuth2AccessToken accessToken = new OAuth2AccessToken(TokenType.BEARER, generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(), generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
                OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient).principalName(clientPrincipal.getName()).authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).authorizedScopes((Set)authorizedScopes);
                if (generatedAccessToken instanceof ClaimAccessor) {
                    authorizationBuilder.token(accessToken, (metadata) -> {
                        metadata.put(Token.CLAIMS_METADATA_NAME, ((ClaimAccessor)generatedAccessToken).getClaims());
                    });
                } else {
                    authorizationBuilder.accessToken(accessToken);
                }

                OAuth2Authorization authorization = authorizationBuilder.build();
                this.authorizationService.save(authorization);
                if (log.isTraceEnabled()) {
                    log.trace("Saved authorization");
                    log.trace("Authenticated token request");
                }
                Authentication auth = new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
                return auth;
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
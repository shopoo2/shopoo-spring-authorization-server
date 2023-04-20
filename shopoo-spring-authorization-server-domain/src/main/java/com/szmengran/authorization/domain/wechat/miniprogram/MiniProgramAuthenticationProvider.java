package com.szmengran.authorization.domain.wechat.miniprogram;

import com.szmengran.authorization.domain.utils.OAuth2AuthenticationProviderUtils;
import com.szmengran.authorization.domain.wechat.config.WechatProperties;
import com.szmengran.authorization.domain.wechat.repository.MiniProgramRepository;
import com.szmengran.authorization.dto.WechatMiniProgramCO;
import com.szmengran.authorization.dto.cqe.WechatMiniProgramQuery;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 19:36
 * @Version 1.0
 */
public class MiniProgramAuthenticationProvider implements AuthenticationProvider {
    
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private final WechatProperties wechatProperties;
    private final MiniProgramRepository miniProgramRepository;
    
    public MiniProgramAuthenticationProvider(WechatProperties wechatProperties, MiniProgramRepository miniProgramRepository, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(wechatProperties, "wechatProperties cannot be null");
        Assert.notNull(miniProgramRepository, "miniProgramRepository cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.wechatProperties = wechatProperties;
        this.miniProgramRepository = miniProgramRepository;
        this.tokenGenerator = tokenGenerator;
    }
    
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        MiniProgramAuthorizationToken miniProgramAuthorizationToken = (MiniProgramAuthorizationToken) authentication;
        String appid = miniProgramAuthorizationToken.getAppid();
        Assert.notNull(appid, "appid cannot be null");
        String secret = wechatProperties.getMiniProgram().getMap().get(appid);
        Assert.notNull(secret, String.format("can't found secret(appid: %s) from map", appid));
        WechatMiniProgramQuery wechatMiniProgramQuery = WechatMiniProgramQuery.builder().appid(appid).secret(secret).jsCode(miniProgramAuthorizationToken.getCode()).grantType("authorization_code").build();
        WechatMiniProgramCO wechatMiniProgramCO = miniProgramRepository.jscode2session(wechatMiniProgramQuery);
    
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(miniProgramAuthorizationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder().registeredClient(registeredClient).principal(miniProgramAuthorizationToken).authorizationServerContext(AuthorizationServerContextHolder.getContext()).authorizedScopes(miniProgramAuthorizationToken.getScopes()).authorizationGrantType(MiniProgramAuthorizationToken.GRANT_TYPE);
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        OAuth2AccessToken accessToken = new OAuth2AccessToken(TokenType.BEARER, generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(), generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, null, Collections.emptyMap());
    }
    
    @Override
    public boolean supports(final Class<?> authentication) {
        return MiniProgramAuthorizationToken.class.isAssignableFrom(authentication);
    }
}

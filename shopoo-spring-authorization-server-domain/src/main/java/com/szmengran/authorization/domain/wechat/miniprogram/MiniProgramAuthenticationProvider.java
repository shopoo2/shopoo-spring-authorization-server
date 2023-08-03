package com.szmengran.authorization.domain.wechat.miniprogram;

import com.shopoo.wechat.dto.clientobject.LoginInfoCO;
import com.szmengran.authorization.domain.admin.converter.Converter;
import com.szmengran.authorization.domain.admin.entity.Oauth2Wechat;
import com.szmengran.authorization.domain.admin.repository.UserRepository;
import com.szmengran.authorization.domain.admin.repository.WechatRepository;
import com.szmengran.authorization.domain.admin.valueobject.UserDetailsExt;
import com.szmengran.authorization.domain.utils.IDUtils;
import com.szmengran.authorization.domain.utils.OAuth2AuthenticationProviderUtils;
import com.szmengran.authorization.domain.wechat.config.WechatProperties;
import com.szmengran.authorization.domain.wechat.repository.MiniProgramRepository;
import com.szmengran.authorization.dto.UserDetailsCO;
import com.szmengran.authorization.dto.cqe.WechatMiniProgramQuery;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 19:36
 * @Version 1.0
 */
public class MiniProgramAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private final WechatProperties wechatProperties;
    private final UserDetailsService userDetailsService;
    
    private final MiniProgramRepository miniProgramRepository;
    
    private final WechatRepository wechatRepository;
    
    public MiniProgramAuthenticationProvider(WechatRepository wechatRepository, UserDetailsService userDetailsService, WechatProperties wechatProperties, MiniProgramRepository miniProgramRepository, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(wechatRepository, "wechatRepository cannot be null");
        Assert.notNull(userDetailsService, "userDetailsService cannot be null");
        Assert.notNull(wechatProperties, "wechatProperties cannot be null");
        Assert.notNull(miniProgramRepository, "miniProgramRepository cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.wechatRepository = wechatRepository;
        this.userDetailsService = userDetailsService;
        this.wechatProperties = wechatProperties;
        this.miniProgramRepository = miniProgramRepository;
        this.tokenGenerator = tokenGenerator;
    }
    
    @Override
    protected void additionalAuthenticationChecks(final UserDetails userDetails, final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    
    }
    
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        MiniProgramAuthorizationToken miniProgramAuthorizationToken = (MiniProgramAuthorizationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(miniProgramAuthorizationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
    
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = checkAndRegister(registeredClient, miniProgramAuthorizationToken.getCode());
    
        Set<String> scopes = Optional.ofNullable(miniProgramAuthorizationToken.getScopes()).orElse(registeredClient.getScopes());
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder().registeredClient(registeredClient).principal(usernamePasswordAuthenticationToken).authorizationServerContext(AuthorizationServerContextHolder.getContext()).authorizedScopes(scopes).authorizationGrantType(MiniProgramAuthorizationToken.GRANT_TYPE);
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        OAuth2AccessToken accessToken = new OAuth2AccessToken(TokenType.BEARER, generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(), generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, null, Collections.emptyMap());
    }
    
    private UsernamePasswordAuthenticationToken checkAndRegister(RegisteredClient registeredClient, String code) {
        String appId = registeredClient.getClientId();
        Assert.notNull(appId, "appId cannot be null");
        String secret = wechatProperties.getMiniProgram().getMap().get(appId);
        Assert.notNull(secret, String.format("can't found secret(appId: %s) from map", appId));
        WechatMiniProgramQuery wechatMiniProgramQuery = WechatMiniProgramQuery.builder().appId(appId).secret(secret).jsCode(code).grantType("authorization_code").build();
        LoginInfoCO loginInfoCO = miniProgramRepository.login(wechatMiniProgramQuery);
        UserDetailsExt loadedUser = (UserDetailsExt) userDetailsService.loadUserByUsername(loginInfoCO.getOpenid());
        if (loadedUser == null) {
            Oauth2Wechat oauth2Wechat = new Oauth2Wechat();
            String id = IDUtils.getSnowId("");
            oauth2Wechat.setUserId(id);
            oauth2Wechat.setOpenid(loginInfoCO.getOpenid());
            oauth2Wechat.setUnionid(loginInfoCO.getUnionid());
            wechatRepository.add(oauth2Wechat);
            loadedUser = new UserDetailsExt();
            loadedUser.setUserId(id);
            loadedUser.setUsername(loginInfoCO.getUnionid());
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(loginInfoCO.getUnionid(), null);
        super.createSuccessAuthentication(loginInfoCO.getUnionid(), usernamePasswordAuthenticationToken, loadedUser);
        return usernamePasswordAuthenticationToken;
    
    }
    @Override
    protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        try {
            UserDetails loadedUser = userDetailsService.loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        }
        catch (UsernameNotFoundException ex) {
            throw ex;
        }
        catch (InternalAuthenticationServiceException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public boolean supports(final Class<?> authentication) {
        return MiniProgramAuthorizationToken.class.isAssignableFrom(authentication);
    }
}

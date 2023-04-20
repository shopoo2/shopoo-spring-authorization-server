package com.szmengran.authorization.domain.config;

import com.szmengran.authorization.domain.password.UsernamePasswordAuthenticationProvider;
import com.szmengran.authorization.domain.password.UsernamePasswordAuthorizationConverter;
import com.szmengran.authorization.domain.wechat.config.WechatProperties;
import com.szmengran.authorization.domain.wechat.miniprogram.MiniProgramAuthenticationProvider;
import com.szmengran.authorization.domain.wechat.miniprogram.MiniProgramAuthorizationConverter;
import com.szmengran.authorization.domain.wechat.miniprogram.MiniProgramAuthorizationToken;
import com.szmengran.authorization.domain.wechat.repository.MiniProgramRepository;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/12 20:12
 * @Version 1.0
 */
@Configuration
public class SecurityConfig {
    
    @Resource
    private JdbcTemplate jdbcTemplate;
    
    @Resource
    private JwtDecoder jwtDecoder;
    
    @Resource
    private UserDetailsService userDetailsService;
    
    @Resource
    private PasswordEncoder passwordEncoder;
    
    @Resource
    private WechatProperties wechatProperties;
    
    @Resource
    private MiniProgramRepository miniProgramRepository;
    
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        authorizationServerConfigurer.oidc(Customizer.withDefaults());
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .apply(authorizationServerConfigurer);
//        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
//        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
//        http.csrf().disable().securityMatcher(endpointsMatcher).authorizeHttpRequests((authorize) -> {
//            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)authorize.anyRequest()).authenticated();
//        }).apply(authorizationServerConfigurer);
//
//        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
//                .oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0
//        http
//                // Redirect to the login page when not authenticated from the
//                // authorization endpoint
//                .exceptionHandling((exceptions) -> exceptions
//                        .authenticationEntryPoint(
//                                new LoginUrlAuthenticationEntryPoint("/login"))
//                )
//                // Accept access tokens for User Info and/or Client Registration
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        
        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(http);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(http);
    
        authorizationServerConfigurer.tokenEndpoint(tokenEndpoint  ->
                        tokenEndpoint
                                .accessTokenRequestConverter(new UsernamePasswordAuthorizationConverter())
                                .accessTokenRequestConverter(new MiniProgramAuthorizationConverter())
                                //                                .authorizationRequestConverters(authorizationRequestConvertersConsumer)
                                .authenticationProvider(new UsernamePasswordAuthenticationProvider(passwordEncoder, userDetailsService, authorizationService, tokenGenerator))
                                .authenticationProvider(new MiniProgramAuthenticationProvider(wechatProperties, miniProgramRepository, tokenGenerator))
                //                                .authenticationProviders(authenticationProvidersConsumer)
                //                                .authorizationResponseHandler(authorizationResponseHandler)
                //                                .errorResponseHandler(errorResponseHandler)
                //                                .consentPage("/oauth2/v1/authorize")
        );
        
        return http.build();
    }
    
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults());
        
        return http.build();
    }
    
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
//        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("shopoo")
//                .clientSecret("{noop}jksj83sjf02kjsfhlfsljsoiw39janxie")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
//                .redirectUri("http://127.0.0.1:8080/authorized")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .scope("message.read")
//                .scope("message.write")
//                .scope("shopoo")
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.of(14, ChronoUnit.DAYS)).build())
//                .build();
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }
    
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }
}

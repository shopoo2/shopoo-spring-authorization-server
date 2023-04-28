package com.szmengran.authorization.domain.config;

import com.szmengran.authorization.domain.password.UsernamePasswordAuthenticationProvider;
import com.szmengran.authorization.domain.password.UsernamePasswordAuthorizationConverter;
import com.szmengran.authorization.domain.wechat.config.WechatProperties;
import com.szmengran.authorization.domain.wechat.miniprogram.MiniProgramAuthenticationProvider;
import com.szmengran.authorization.domain.wechat.miniprogram.MiniProgramAuthorizationConverter;
import com.szmengran.authorization.domain.wechat.repository.MiniProgramRepository;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;

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
                .authorizeHttpRequests().requestMatchers("/rsa/publicKey").permitAll().and()
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .apply(authorizationServerConfigurer);
        
        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(http);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(http);
    
        authorizationServerConfigurer.tokenEndpoint(tokenEndpoint  ->
                        tokenEndpoint
                                .accessTokenRequestConverter(new UsernamePasswordAuthorizationConverter())
                                .accessTokenRequestConverter(new MiniProgramAuthorizationConverter())
                                .authenticationProvider(new UsernamePasswordAuthenticationProvider(passwordEncoder, userDetailsService, authorizationService, tokenGenerator))
                                .authenticationProvider(new MiniProgramAuthenticationProvider(wechatProperties, miniProgramRepository, tokenGenerator))
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
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }
    
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }
}

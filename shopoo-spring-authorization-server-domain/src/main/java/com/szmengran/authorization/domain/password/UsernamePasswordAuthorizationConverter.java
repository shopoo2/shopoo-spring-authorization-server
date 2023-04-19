package com.szmengran.authorization.domain.password;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author MaoYuan.Li
 * @Date 2023/3/8 16:51
 * @Version 1.0
 */
public class UsernamePasswordAuthorizationConverter implements AuthenticationConverter {


    @Override
    public Authentication convert(final HttpServletRequest request) {
        String grantType = request.getParameter(Constants.GRANT_TYPE);
        if (!Constants.PASSWORD_AUTHORIZATION_GRANT_TYPE.equals(grantType)) {
            return null;
        } else {
            String username = request.getParameter(Constants.USERNAME_KEY);
            String password = request.getParameter(Constants.PASSWORD_KEY);

            if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
                throw new BadCredentialsException("Invalid username or password");
            }

            Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
            MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
            String scope = parameters.getFirst(Constants.SCOPE);
            if (StringUtils.hasText(scope) && ((List)parameters.get(Constants.SCOPE)).size() != 1) {
                OAuth2EndpointUtils.throwError("invalid_request", Constants.SCOPE, "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2");
            }

            Set<String> requestedScopes = null;
            if (StringUtils.hasText(scope)) {
                requestedScopes = new HashSet(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
            }

            Map<String, Object> additionalParameters = new HashMap();
            parameters.forEach((key, value) -> {
                if (!key.equals("grant_type") && !key.equals(Constants.SCOPE)) {
                    additionalParameters.put(key, value.get(0));
                }

            });
    
            return new CustomerUsernamePasswordAuthenticationToken(clientPrincipal, requestedScopes, additionalParameters);
        }
    }
}

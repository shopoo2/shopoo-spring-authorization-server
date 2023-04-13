package com.szmengran.authorization.domain.password;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author MaoYuan.Li
 * @Date 2023/3/8 17:05
 * @Version 1.0
 */
public class OAuth2EndpointUtils {

    static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private OAuth2EndpointUtils() {
    }

    static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            if (values.length > 0) {
                String[] var3 = values;
                int var4 = values.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    String value = var3[var5];
                    parameters.add(key, value);
                }
            }

        });
        return parameters;
    }

    static Map<String, Object> getParametersIfMatchesAuthorizationPasswordGrantRequest(HttpServletRequest request, String... exclusions) {
        if (!matchesAuthorizationCodeGrantRequest(request)) {
            return Collections.emptyMap();
        } else {
            Map<String, Object> parameters = new HashMap(getParameters(request).toSingleValueMap());
            String[] var3 = exclusions;
            int var4 = exclusions.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String exclusion = var3[var5];
                parameters.remove(exclusion);
            }

            return parameters;
        }
    }

    static boolean matchesAuthorizationCodeGrantRequest(HttpServletRequest request) {
        return AuthorizationGrantType.PASSWORD.getValue().equals(request.getParameter("grant_type"));
    }

    static void throwError(String errorCode, String parameterName, String errorUri) {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
        throw new OAuth2AuthenticationException(error);
    }
}

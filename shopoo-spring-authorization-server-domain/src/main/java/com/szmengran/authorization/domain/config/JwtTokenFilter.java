//package com.szmengran.authorization.domain.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// *
// * @Author <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
// * @Date 2023/3/19 08:47
// */
//public class JwtTokenFilter extends OncePerRequestFilter {
//	private final JwtDecoder jwtDecoder;
//	private final String AUTHORIZATION_HEADER = "Authorization";
//	private final String TOKEN_TYPE = "Bearer ";
//
//	public JwtTokenFilter(JwtDecoder jwtDecoder) {
//		this.jwtDecoder = jwtDecoder;
//	}
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
//		if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE)) {
//			String token = authorizationHeader.substring(TOKEN_TYPE.length());
//			try {
//				Jwt jwt = jwtDecoder.decode(token);
//				Map<String, Object> map = jwt.getClaims();
//				String username = (String) map.get("username");
//				String authorities = (String) map.get("authorities");
//				List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//			} catch (Exception e) {
//				throw new BadCredentialsException("Invalid token");
//			}
//		}
//		filterChain.doFilter(request, response);
//	}
//}

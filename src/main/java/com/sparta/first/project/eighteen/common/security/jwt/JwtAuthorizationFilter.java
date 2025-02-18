package com.sparta.first.project.eighteen.common.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparta.first.project.eighteen.common.security.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 검증 및 인가")
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	private final String AUTHORIZATION_HEADER = "Authorization";
	private final String BEARER_PREFIX = "Bearer ";
	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getRequestURI().matches("^/api/v1/auth/");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		// 인가 필터는 JWT 검증하고, SecurityContext에 Authentication을 저장

		// 1. JWT 검증
		String token = request.getHeader(AUTHORIZATION_HEADER);

		if (!StringUtils.hasText(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		token = token.substring(BEARER_PREFIX.length());
		jwtUtil.validateAccessToken(token);

		// 2. JWT에서 아이디 추출
		String userId = jwtUtil.getUserUUID(token);

		setAuthentication(userId);
		filterChain.doFilter(request, response);
	}

	// 4. SecurityContext에 Authentication 객체 저장
	private void setAuthentication(String userId) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = createAuthentication(userId);

		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
	}

	// 3. 인가해줄 Authentication 생성
	private UsernamePasswordAuthenticationToken createAuthentication(String userId) {
		UserDetails userDetails = userDetailsService.loadUserByUserUUID(userId);
		return new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities()
		);
	}

}

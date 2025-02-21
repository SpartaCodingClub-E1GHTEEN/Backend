package com.sparta.first.project.eighteen.common.security.jwt;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparta.first.project.eighteen.common.security.UserDetailsImpl;
import com.sparta.first.project.eighteen.common.security.UserDetailsServiceImpl;
import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.Users;
import com.sparta.first.project.eighteen.utils.UserUtils;

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

		// 2. JWT에서 아이디 추출 & 권한 추출
		String userId = jwtUtil.getUserUUID(token);
		Role userRole = jwtUtil.getUserRole(token);
		LocalDateTime issuedAt = jwtUtil.getIssuedAt(token);

		UserUtils.checkAccessTokenBlocked(UUID.fromString(userId), issuedAt);

		setAuthentication(userId, userRole);
		filterChain.doFilter(request, response);
	}

	// 4. SecurityContext에 Authentication 객체 저장
	private void setAuthentication(String userId, Role userRole) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = createJwtAuthentication(userId, userRole);

		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
	}

	/**
	 * UserUUID로 DB에서 조회한 후 인증 주체를 만드는 메서드
	 * @param userId
	 * @return
	 */
	private UsernamePasswordAuthenticationToken createAuthentication(String userId) {
		UserDetails userDetails = userDetailsService.loadUserByUserUUID(userId);
		return new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities()
		);
	}

	/**
	 * Jwt 토큰을 기반으로 인증 주체를 만드는 메서드
	 * @param userId
	 * @param userRole
	 * @return
	 */
	private UsernamePasswordAuthenticationToken createJwtAuthentication(String userId, Role userRole) {
		Users jwtUser = Users.builder()
			.userId(UUID.fromString(userId))
			.role(userRole)
			.build();

		UserDetails userDetails = new UserDetailsImpl(jwtUser);

		return new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities()
		);
	}

}

package com.sparta.first.project.eighteen.common.security.jwt;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.common.exception.BaseException;
import com.sparta.first.project.eighteen.common.security.UserDetailsImpl;
import com.sparta.first.project.eighteen.domain.users.dtos.LoginRequestDto;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "로그인 및 JWT 발급")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtUtil jwtUtil;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void init() {
		setFilterProcessesUrl("/api/v1/auth/sign-in");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		try {
			LoginRequestDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword(), null));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		// 아이디와 권한으로 JWT 생성
		String username = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
		String authorities = ((UserDetailsImpl)authResult.getPrincipal()).getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		String token = jwtUtil.generateAccessToken(username, authorities);
		ApiResponse<Map<String, String>> tokenResponse = ApiResponse.ok("로그인 성공",
			Collections.singletonMap("token", token));

		setContentTypeAndEncoding(response);

		response.getWriter().write(objectMapper.writeValueAsString(tokenResponse));
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {

		ApiResponse<Object> fail = ApiResponse.fail(
			new BaseException("로그인에 실패했습니다. 다시 시도해주세요.", -1, HttpStatus.UNAUTHORIZED));

		setContentTypeAndEncoding(response);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		response.getWriter().write(objectMapper.writeValueAsString(fail));
	}

	private void setContentTypeAndEncoding(HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
	}
}

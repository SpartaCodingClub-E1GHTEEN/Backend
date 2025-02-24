package com.sparta.first.project.eighteen.common.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.sparta.first.project.eighteen.common.exception.BaseException;
import com.sparta.first.project.eighteen.model.users.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j(topic = "JwtUtil")
public class JwtUtil {
	@Value("${jwt.access.exp}")
	private int accessTokenExp;

	@Value("${jwt.refresh.exp}")
	private int refreshTokenExp;

	private String ISSUER = "eighteen";
	private final String AUTHORIZATION_KEY = "auth";

	private SecretKey secretKey;

	public JwtUtil(@Value("${jwt.secret}") String salt,
		@Value("${jwt.access.exp}") int accessTokenExp,
		@Value("${jwt.refresh.exp}") int refreshTokenExp) {
		this.accessTokenExp = accessTokenExp;
		this.refreshTokenExp = refreshTokenExp;
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(salt));
	}

	// generation - 생성
	public String generateAccessToken(String userUUID, String authorities) {
		Date now = new Date();

		return Jwts.builder()
			.subject(userUUID)
			.claim(AUTHORIZATION_KEY, authorities)
			.issuer(ISSUER)
			.issuedAt(now)
			.expiration(Date.from(now.toInstant().plusMillis(accessTokenExp * 1_000L)))
			.signWith(secretKey, Jwts.SIG.HS512)
			.compact();
	}

	public void validateAccessToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token);
		} catch (SignatureException | MalformedJwtException e) {
			log.error("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.error("만료된 JWT 서명입니다.");
		} catch (UnsupportedJwtException e) {
			log.error("지원되지 않는 JWT 서명입니다.");
		} catch (IllegalArgumentException e) {
			log.error("JWT 토큰이 잘못 되었습니다.");
		}
	}

	// 데이터 추출
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(accessToken)
				.getPayload();
		} catch (JwtException e) {
			throw new BaseException("로그인 중 문제가 생겼습니다. 다시 시도해주세요.", -1, HttpStatus.UNAUTHORIZED);
		}
	}

	// userUUID 추출
	public String getUserUUID(String accessToken) {
		return parseClaims(accessToken).getSubject();
	}

	/**
	 * Jwt 토큰 내의 auth 클레임을 기준으로 사용자 역할 부여
	 * @param accessToken
	 * @return 부여받은 역할군
	 */
	public Role getUserRole(String accessToken) {
		return Role.from(parseClaims(accessToken).get(AUTHORIZATION_KEY, String.class));
	}

	public LocalDateTime getIssuedAt(String accessToken) {
		Date issuedAt = parseClaims(accessToken).getIssuedAt();

		return issuedAt.toInstant()
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
	}
}

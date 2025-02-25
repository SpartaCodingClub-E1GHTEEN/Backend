package com.sparta.first.project.eighteen.domain.users;

import java.util.UUID;

import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.first.project.eighteen.common.exception.UserException;
import com.sparta.first.project.eighteen.common.security.jwt.JwtUtil;
import com.sparta.first.project.eighteen.common.security.jwt.RefreshToken;
import com.sparta.first.project.eighteen.domain.users.dtos.UserRequestDto;
import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.TokenStatus;
import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtUtil jwtUtil;
	private static final String REFRESH_TOKEN = "RefreshToken";

	public void saveUser(UserRequestDto userRequestDto) {
		Users entity = userRequestDto.toEntity(passwordEncoder);
		userRepository.save(entity);
	}

	public String reissueAccessToken(String refreshToken, HttpServletResponse response) {
		TokenStatus tokenStatus = jwtUtil.validateRefreshToken(refreshToken);

		if (!tokenStatus.equals(TokenStatus.IS_VALID)) {
			// 쿠키 삭제
			HttpCookie cookie = generateCookie(null, 0);
			response.setHeader("Set-Cookie", cookie.toString());
			throw new UserException.RefreshTokenExpired();
		}

		UUID userUUID = UUID.fromString(jwtUtil.getUserUUID(refreshToken));
		Role userRole = jwtUtil.getUserRole(refreshToken);

		String findRefreshToken = refreshTokenRepository.findByUserUUID(userUUID)
			.orElseThrow(UserException.RefreshTokenInValid::new);

		if (!findRefreshToken.equals(refreshToken)) {
			// 쿠키 삭제
			HttpCookie cookie = generateCookie(null, 0);
			response.setHeader("Set-Cookie", cookie.toString());

			// 해당 userUUID로 만든 토큰도 무효화
			refreshTokenRepository.remove(userUUID);

			throw new UserException.RefreshTokenInValid();
		}

		// 정상 발급
		String accessToken = jwtUtil.generateAccessToken(userUUID.toString(), userRole.getAuthority());

		// 리프레시 토큰
		RefreshToken generatedRefreshToken = jwtUtil.generateRefreshToken(userUUID.toString(), userRole.getAuthority());

		refreshTokenRepository.save(userUUID, generatedRefreshToken.getRefreshToken());
		ResponseCookie cookie = generateCookie(generatedRefreshToken.getRefreshToken(),
			generatedRefreshToken.getExpiresIn());
		
		response.setHeader("Set-Cookie", cookie.toString());
		return accessToken;
	}

	private static ResponseCookie generateCookie(String refreshToken, int expiresIn) {
		return ResponseCookie.from(REFRESH_TOKEN, refreshToken)
			.httpOnly(true)
			.maxAge(expiresIn)
			.secure(true)
			.path("/")
			.sameSite("None")
			.build();
	}
}

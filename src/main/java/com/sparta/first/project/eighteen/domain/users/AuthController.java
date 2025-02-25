package com.sparta.first.project.eighteen.domain.users;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.users.dtos.UserRequestDto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody UserRequestDto userRequestDto) {
		authService.saveUser(userRequestDto);
		return ResponseEntity.ok(ApiResponse.ok("회원가입 성공", null));
	}

	/**
	 * TODO: 추후 rateLimit 적용
	 */
	@PostMapping("/reissue-token")
	public ResponseEntity<ApiResponse<Map<String, String>>> reissueToken(
		@CookieValue("RefreshToken") String refreshToken,
		HttpServletResponse response
	) {
		String apiResponse = authService.reissueAccessToken(refreshToken, response);
		return ResponseEntity.ok(ApiResponse.ok(null, Map.of("accessToken", apiResponse)));
	}
}

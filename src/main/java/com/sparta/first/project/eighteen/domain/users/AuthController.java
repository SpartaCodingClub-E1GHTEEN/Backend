package com.sparta.first.project.eighteen.domain.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.users.dtos.UserRequestDto;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	/**
	 *
	 * @param userRequestDto 추후 Valid 적용 예정
	 * @return
	 */
	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody UserRequestDto userRequestDto) {
		return ResponseEntity.ok(ApiResponse.ok("회원가입 성공", null));
	}
}

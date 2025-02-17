package com.sparta.first.project.eighteen.domain.users;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.common.security.UserDetailsImpl;
import com.sparta.first.project.eighteen.domain.users.dtos.UserResponseDto;
import com.sparta.first.project.eighteen.domain.users.dtos.UserUpdateRequestDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@GetMapping
	public ResponseEntity<ApiResponse<UserResponseDto>> findUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		UserResponseDto mockData = UserResponseDto.builder()
			.username("testUser")
			.nickname("테스트회원")
			.phone("012-3456-7890")
			.email("test@test.io")
			.address("서울시 광화문구")
			.build();
		return ResponseEntity.ok(ApiResponse.ok("유저 조회 성공", mockData));
	}

	@PutMapping
	public ResponseEntity<ApiResponse<UserResponseDto>> modifyUser(@RequestBody UserUpdateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		UserResponseDto mockData = UserResponseDto.builder()
			.username("testUser")
			.nickname("테스트회원")
			.phone("012-3456-7890")
			.email("test@test.io")
			.address("서울시 광화문구")
			.build();
		return ResponseEntity.ok(ApiResponse.ok("수정 성공", mockData));
	}

	/**
	 * Jwt토큰에서 유저 UUID를 꺼내 유저를 삭제하는 메서드
	 * @param userDetails
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_CUSTOMER')")
	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		String userId = userDetails.getUser().getUserId();
		return ResponseEntity.ok(ApiResponse.ok("삭제 성공", null));
	}

}

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@ToString
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<ApiResponse<UserResponseDto>> findUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		UserResponseDto response = userService.findUser(userDetails.getUserUUID());
		return ResponseEntity.ok(ApiResponse.ok("유저 조회 성공", response));
	}

	@PutMapping
	public ResponseEntity<ApiResponse<UserResponseDto>> modifyUser(
		@Valid @RequestBody UserUpdateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		log.info("{}", requestDto);
		UserResponseDto responseDto = userService.modifyUser(userDetails.getUserUUID(), requestDto);
		return ResponseEntity.ok(ApiResponse.ok("수정 성공", responseDto));
	}

	/**
	 * Jwt토큰에서 유저 UUID를 꺼내 유저를 삭제하는 메서드
	 * @param userDetails
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_CUSTOMER')")
	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		String userId = userDetails.getUsers().getUserId().toString();
		return ResponseEntity.ok(ApiResponse.ok("삭제 성공", null));
	}

}

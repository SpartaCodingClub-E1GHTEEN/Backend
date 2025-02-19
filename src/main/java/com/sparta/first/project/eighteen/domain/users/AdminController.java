package com.sparta.first.project.eighteen.domain.users;

import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.users.dtos.AdminUserSearchRequestDto;
import com.sparta.first.project.eighteen.domain.users.dtos.AdminUserSearchResponseDto;
import com.sparta.first.project.eighteen.domain.users.dtos.AdminUserUpdateRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/admin/v1")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;

	@GetMapping("/users")
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MASTER')")
	public ResponseEntity<ApiResponse<PagedModel<AdminUserSearchResponseDto>>> getAllUsers(
		@ModelAttribute AdminUserSearchRequestDto requestDto) {

		PagedModel<AdminUserSearchResponseDto> allUsers = adminService.findAllUsers(requestDto);

		return ResponseEntity.ok(ApiResponse.ok("모든 회원 조회", allUsers));
	}

	@PutMapping("/users/{userId}")
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MASTER')")
	public ResponseEntity<ApiResponse<AdminUserSearchResponseDto>> modifyUser(
		@PathVariable String userId,
		@Valid @RequestBody AdminUserUpdateRequestDto requestDto) {

		AdminUserSearchResponseDto response = adminService.modifyUser(userId, requestDto);

		return ResponseEntity.ok(ApiResponse.ok("회원 정보 수정", response));
	}
}

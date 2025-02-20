package com.sparta.first.project.eighteen.domain.users.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.SignUpType;
import com.sparta.first.project.eighteen.model.users.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserSearchResponseDto {
	private UUID userId;
	private String username;
	private String nickname;
	private String email;
	private SignUpType signUpType;
	private Role role;
	private LocalDateTime createdAt;

	public static AdminUserSearchResponseDto from(Users users) {
		return AdminUserSearchResponseDto.builder()
			.userId(users.getUserId())
			.username(users.getUsername())
			.nickname(users.getUserNickname())
			.email(users.getEmail())
			.role(users.getRole())
			.signUpType(users.getSignUpType())
			.createdAt(users.getCreatedAt())
			.build();
	}
}

package com.sparta.first.project.eighteen.domain.users.dtos;

import com.sparta.first.project.eighteen.model.users.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponseDto {
	private String username; // 회원 입력 아이디

	private String nickname; // 회원 닉네임

	private String phone; // 회원 전화번호

	private String email;

	private String address;

	public static UserResponseDto from(Users users) {
		return UserResponseDto.builder()
			.username(users.getUsername())
			.nickname(users.getUserNickname())
			.phone(users.getUserPhone())
			.email(users.getEmail())
			.address(users.getUserAddress())
			.build();
	}
}

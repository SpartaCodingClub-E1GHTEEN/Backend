package com.sparta.first.project.eighteen.domain.users.dtos;

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
}

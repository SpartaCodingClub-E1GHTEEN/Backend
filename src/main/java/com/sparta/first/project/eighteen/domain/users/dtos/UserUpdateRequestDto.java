package com.sparta.first.project.eighteen.domain.users.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {
	@Length(min = 8, max = 15, message = "최소 8자 이상 15자 미만의 비밀번호를 생성해주세요.")
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+=-]{8,15}$")
	private String password;

	@Email
	private String email;

	private String nickname;

	@Pattern(regexp = "^(01[016789])-?\\d{3,4}-?\\d{4}$")
	private String phone;
	
	private String address;
}

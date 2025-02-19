package com.sparta.first.project.eighteen.domain.users.dtos;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
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

	public Users toEntity(PasswordEncoder passwordEncoder) {
		return Users.builder()
			// 새 비밀번호 입력 받을 경우에만 인코딩
			.userPassword(StringUtils.hasText(this.password) ? passwordEncoder.encode(this.password) : null)
			.email(this.email)
			.userNickname(this.nickname)
			.userPhone(this.phone)
			.userAddress(this.address)
			.build();
	}
}

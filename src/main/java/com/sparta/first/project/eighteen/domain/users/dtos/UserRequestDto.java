package com.sparta.first.project.eighteen.domain.users.dtos;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.SignUpType;
import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
	@Length(min = 4, max = 10, message = "최소 4자 이상 10자 이하의 아이디를 생성해주세요.")
	@Pattern(regexp = "^[a-z0-9]{4,10}$")
	private String username; // 회원 입력 아이디

	@Length(min = 8, max = 15, message = "최소 8자 이상 15자 미만의 비밀번호를 생성해주세요.")
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+=-]{8,15}$")
	private String password; // 회원 비밀번호

	private String nickname; // 회원 닉네임

	@Pattern(regexp = "^(01[016789])-?\\d{3,4}-?\\d{4}$")
	private String phone; // 회원 전화번호

	private SignUpType signUpType; // 회원 가입 타입( 자체 서비스 | 소셜 로그인)

	private Role role; // 회원 타입 (고객 | 가게 주인)

	@Email
	private String email;

	private String address;

	public Users toEntity(PasswordEncoder passwordEncoder) {
		return Users.builder()
			.userId(UUID.randomUUID())// 임시로 UUID 생성 후 주입
			.username(this.username)
			.userPassword(passwordEncoder.encode(this.password))
			.userNickname(this.nickname)
			.userPhone(this.phone)
			.userAddress(this.address)
			.signUpType(this.signUpType)
			.email(this.email)
			.role(this.role)
			.isDeleted(false)
			.build();
	}

	public Users toEntityWithUUID(UUID userId) {
		return Users.builder()
			.userId(userId)
			.username(this.username)
			.userPassword(this.password)
			.userNickname(this.nickname)
			.userPhone(this.phone)
			.userAddress(this.address)
			.email(this.email)
			.signUpType(this.signUpType)
			.role(this.role)
			.isDeleted(false)
			.createdBy("50ce7d0a-4ae2-4c47-9842-56bdc29c060d")
			.build();
	}
}

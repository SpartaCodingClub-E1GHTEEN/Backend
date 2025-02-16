package com.sparta.first.project.eighteen.domain.users.dtos;

import org.hibernate.validator.constraints.Length;

import com.sparta.first.project.eighteen.common.security.UserDetailsServiceImpl;

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

	private UserDetailsServiceImpl.User.Role role; // 회원 타입 (고객 | 가게 주인)

	@Email
	private String email;

	private String address;

	enum SignUpType {
		SERVICE("SERVICE"),
		KAKAO("KAKAO"),
		GOOGLE("GOOGLE"),
		NAVER("NAVER");

		private final String client;

		SignUpType(String client) {
			this.client = client;
		}

		public String getClient() {
			return client;
		}
	}
}

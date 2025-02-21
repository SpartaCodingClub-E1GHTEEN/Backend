package com.sparta.first.project.eighteen.domain.users.dtos;

import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserUpdateRequestDto {
	@Email
	private String email;
	private Role role;
	private String password;

	public Users toEntity() {
		return Users.builder()
			.email(this.email)
			.role(this.role)
			.userPassword(this.password)
			.build();
	}
}

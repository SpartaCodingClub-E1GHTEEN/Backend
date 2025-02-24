package com.sparta.first.project.eighteen.domain.users.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.SignUpType;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AdminUserSearchRequestDto {
	@Positive
	private int page = 1;

	@Positive
	private int size = 10;

	private Role role;
	private SignUpType signUpType; // 가입 타입에 따라
	private LocalDateTime dateStart; // 조회 시작일
	private LocalDateTime dateEnd; // 조회 종료일

	@JsonSetter(nulls = Nulls.SKIP)
	private UserSortType sort = UserSortType.SIGN_UP_DATE_ASC; // 가입일 기본값 오름차순

	// page, size, sort의 경우 파라미터 전달이 되지 않은 경우 ->
	public AdminUserSearchRequestDto(int page, int size, Role role, SignUpType signUpType, LocalDateTime dateStart,
		LocalDateTime dateEnd, UserSortType sort) {
		this.page = page != 0 ? page - 1 : this.page;
		this.size = size != 0 ? size : this.size;
		this.sort = sort != null ? sort : this.sort;
		this.role = role;
		this.signUpType = signUpType;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
}

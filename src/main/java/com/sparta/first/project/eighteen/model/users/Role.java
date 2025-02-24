package com.sparta.first.project.eighteen.model.users;

import java.util.Arrays;

import com.sparta.first.project.eighteen.common.exception.UserException;

public enum Role {
	CUSTOMER("ROLE_CUSTOMER"), // 고객
	OWNER("ROLE_OWNER"),       // 가게 주인
	MANAGER("ROLE_MANAGER"),   // 상담원
	RIDER("ROLE_RIDER"),       // 배달원
	MASTER("ROLE_MASTER");     // 관리자 (ADMIN)

	private final String authority;

	Role(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return this.authority;
	}

	/**
	 * 매칭되는 역할을 enum으로 반환해주는 메서드
	 * @param inputRole : Jwt 토큰의 role 값
	 * @return
	 */
	public static Role from(String inputRole) {
		return Arrays.stream(Role.values())
			.filter(role -> role.authority.equals(inputRole))
			.findFirst()
			.orElseThrow(UserException.UserNotFound::new);
	}
}
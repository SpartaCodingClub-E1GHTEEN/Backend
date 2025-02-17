package com.sparta.first.project.eighteen.model.users;

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
}
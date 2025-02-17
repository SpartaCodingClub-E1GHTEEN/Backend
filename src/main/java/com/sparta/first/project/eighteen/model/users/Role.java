package com.sparta.first.project.eighteen.model.users;

public enum Role {
	CUSTOMER("ROLE_CUSTOMER"),
	OWNER("ROLE_OWNER"),
	MANAGER("ROLE_MANAGER"),
	MASTER("ROLE_MASTER");

	private final String authority;

	Role(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return this.authority;
	}
}

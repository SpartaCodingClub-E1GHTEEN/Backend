package com.sparta.first.project.eighteen.model.users;

public enum SignUpType {
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

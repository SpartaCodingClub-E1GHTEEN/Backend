package com.sparta.first.project.eighteen.model.users;

public enum SignUpType {
	SERVICE("SERVICE"), // 자체 서비스 가입
	KAKAO("KAKAO"),     // 소셜 로그인 - 카카오
	GOOGLE("GOOGLE"),   // 소셜 로그인 - 구글
	NAVER("NAVER");     // 소셜 로그인 - 네이버

	private final String client;

	SignUpType(String client) {
		this.client = client;
	}

	public String getClient() {
		return client;
	}
}

package com.sparta.first.project.eighteen.common.security.jwt;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshToken {
	private String refreshToken;

	private UUID userUUID; // 키 값

	private int expiresIn;

	@Builder
	public RefreshToken(String refreshToken, UUID userUUID, int expiresIn) {
		this.refreshToken = refreshToken;
		this.userUUID = userUUID;
		this.expiresIn = expiresIn;
	}

}

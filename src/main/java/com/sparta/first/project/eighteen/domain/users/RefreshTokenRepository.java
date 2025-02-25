package com.sparta.first.project.eighteen.domain.users;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {
	// userUUID, RefreshToken 값 저장
	private final ConcurrentHashMap<UUID, String> refreshTokens = new ConcurrentHashMap<>();

	public Optional<String> findByUserUUID(UUID userUUID) {
		return Optional.ofNullable(refreshTokens.getOrDefault(userUUID, null));
	}

	public void save(UUID userUUID, String refreshToken) {
		refreshTokens.put(userUUID, refreshToken);
	}

	public void remove(UUID userUUID) {
		refreshTokens.remove(userUUID);
	}
}

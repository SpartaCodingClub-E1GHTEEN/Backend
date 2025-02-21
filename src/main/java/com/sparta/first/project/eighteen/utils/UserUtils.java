package com.sparta.first.project.eighteen.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sparta.first.project.eighteen.common.exception.UserException;
import com.sparta.first.project.eighteen.common.security.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

// @Component

/**
 * Util 클래스를 static 메서드로만 구성할지? <br>
 * 빈으로 등록해서 싱글톤으로 관리할지? <br>
 * - 이 경우 @Value 어노테이션 활용 yml 파일에서 값 주입 받을 수 있음.
 *
 */
@Slf4j(topic = "액세스 토큰 발급 시점 검증")
public class UserUtils {
	// TODO: 추후 권한 변경이나 삭제의 경우 AOP로 변경
	private static final ConcurrentHashMap<UUID, LocalDateTime> isUserModified = new ConcurrentHashMap<>();

	/**
	 * 유저의 권한 변경 또는 탈퇴 시 해당 시간 기록 <br>
	 * @Param userUUID <br>
	 * - 관리자가 유저 권한 변경 하는 경우 PathVariable로 전달받은 값 <br>
	 * - 유저가 탈퇴한 경우 유저 본인의 식별자 값  <br>
	 * 이후 인가 과정에서는 먼저 UUID로 변경 내역이 있는지 확인
	 */
	public static void markUserModified(UUID userUUID) {
		LocalDateTime now = LocalDateTime.now();

		// 권한 변경이나 삭제 시간 매핑
		isUserModified.put(userUUID, now);
	}

	/**
	 * userUUID로 조회 했을 때 <br>
	 * - null인 경우 -> 변경된 적이 없음, 정상적으로 인가 <br>
	 * - 액세스 토큰 발급 시점 >= 유저 변경 시점 -> 변경 내용 반영되었기 때문에 정상 처리 <br>
	 * - 액세스 토큰 발급 시점 < 유저 변경 시점 -> 변경 이전에 발급한 토큰이기 때문에 반영이 되어있지 않음, 차단
	 * @param userUUID : 검색할 유저 UUID
	 */
	public static void checkAccessTokenBlocked(UUID userUUID, LocalDateTime issuedAt) {
		LocalDateTime modifiedAt = isUserModified.getOrDefault(userUUID, null);
		//
		if (modifiedAt != null && issuedAt.isBefore(modifiedAt)) {
			log.error("[ERROR] 권한 변경 / 탈퇴 이전에 발급된 토큰입니다.");
			throw new UserException.UserNotFound();
		}

		if (modifiedAt != null) {
			deleteIfExpired(userUUID, modifiedAt);
		}
	}

	/**
	 * 추후 scheduler나 Redis TTL 사용해서 이미 만료기간이 지난 경우 제거
	 */
	public static void deleteIfExpired(UUID userUUID, LocalDateTime modifiedAt) {
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.ofMillis(1209600 * 1_000L);
		LocalDateTime modifiedAtPlusAccessTokenExp = modifiedAt.plus(duration);

		// 현재 시점이 변경 시점 + 만료 기간 보다 이전일 경우 남겨둬야 함.
		if (now.isBefore(modifiedAtPlusAccessTokenExp)) {
			return;
		}

		log.info("now = {}, 변경 시점 + 만료기간 = {}", now, modifiedAtPlusAccessTokenExp);
		// 현재 시점이 변경 시점 + 만료 기간 보다 이후라면 아무리 빨리 발급해도 변경 시점 이후에 발급한 것.
		isUserModified.remove(userUUID);

	}

	/**
	 * 로그인 이후 상황에서 인증 주체 가져오는 메서드
	 * @return 인증에 성공한 UserDetailsImpl
	 */
	public static UserDetailsImpl getUserDetails() {
		SecurityContext context = SecurityContextHolder.getContext();
		return (UserDetailsImpl)context.getAuthentication().getPrincipal();
	}
}

package com.sparta.first.project.eighteen.model.users;

import java.util.Optional;
import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Table(name = "p_users")
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Users extends BaseEntity {
	@Id
	// @GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID userId; // 유저 식별자

	@Column(unique = true, updatable = false, nullable = false)
	private String username; // 유저 입력 아이디

	@Column(nullable = false)
	private String userPassword; // 유저 비밀번호

	@Column(nullable = false, unique = true)
	private String userNickname;

	@Column(nullable = false)
	private String userPhone;

	@Column(nullable = false)
	private String userAddress;

	@Column(nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role; // 권한

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private SignUpType signUpType;

	/**
	 * 비밀번호, 이메일, 닉네임, 핸드폰번호, 주소 중
	 * 존재하는 필드에 대해서만 업데이트
	 * @param update : 사용자에게 전달받은 유저 변경 DTO
	 */
	public void updateUser(Users update) {
		Optional.ofNullable(update.getUserPassword()).ifPresent(password -> this.userPassword = password);
		Optional.ofNullable(update.getEmail()).ifPresent(email -> this.email = email);
		Optional.ofNullable(update.getUserNickname()).ifPresent(nickname -> this.userNickname = nickname);
		Optional.ofNullable(update.getUserPhone()).ifPresent(phone -> this.userPhone = phone);
		Optional.ofNullable(update.getUserAddress()).ifPresent(address -> this.userAddress = address);
	}

	/**
	 * 관리자 권한으로 유저 수정
	 * 이 때는 비밀번호, 권한, 이메일에 대해서 변경해줄 수 있음.
	 *
	 * TODO: 추후 비밀번호를 직접 입력받아서 수정하는 것이 아닌 시스템 내부적으로 생성해서 수정하도록 변경
	 *
	 * @param update
	 */
	public void adminUserUpdate(Users update) {
		Optional.ofNullable(update.getEmail()).ifPresent(email -> this.email = email);
		Optional.ofNullable(update.getUserPassword()).ifPresent(password -> this.userPassword = password);
		Optional.ofNullable(update.getRole()).ifPresent(role -> this.role = role);
	}
}

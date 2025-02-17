package com.sparta.first.project.eighteen.model.users;

import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "p_users")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
	@Id
	// @GeneratedValue(strategy = GenerationType.UUID)
	private UUID userId; // 유저 식별자

	@Column(unique = true, updatable = false, nullable = false)
	private String username; // 유저 입력 아이디

	@Column(nullable = false)
	private String userPassword; // 유저 비밀번호

	@Column(nullable = false)
	private String userPhone;

	@Column(nullable = false)
	private String userAddress;

	@Column(nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role; // 권한

}

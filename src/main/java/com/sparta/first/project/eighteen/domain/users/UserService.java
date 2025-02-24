package com.sparta.first.project.eighteen.domain.users;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.first.project.eighteen.common.exception.BaseException;
import com.sparta.first.project.eighteen.domain.users.dtos.UserResponseDto;
import com.sparta.first.project.eighteen.domain.users.dtos.UserUpdateRequestDto;
import com.sparta.first.project.eighteen.model.users.Users;
import com.sparta.first.project.eighteen.utils.UserUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserResponseDto findUser(UUID userId) {
		Users users = findUsers(userId);

		return UserResponseDto.from(users);
	}

	/**
	 * @param userUUID : Authentication에서 추출한 유저 UUID
	 * @param requestDto : 유저 변경 정보를 담은 DTO (필드 null 가능)
	 * @return
	 */
	@Transactional
	public UserResponseDto modifyUser(UUID userUUID, UserUpdateRequestDto requestDto) {
		Users users = findUsers(userUUID);
		Users update = requestDto.toEntity(passwordEncoder);

		users.updateUser(update);

		return UserResponseDto.from(users);
	}

	@Transactional
	public void deleteUser(UUID userId) {
		Users users = findUsers(userId);

		// 유저의 탈퇴 시 변경 시점 기록
		// TODO: 추후 해당 메서드에서 예외 발생 시 AOP로 기록한 아이디 취소하는 방법 고려
		UserUtils.markUserModified(userId);

		users.delete(true, userId.toString());
	}

	private Users findUsers(UUID userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new BaseException("회원을 찾을 수 없습니다.", -1, HttpStatus.NOT_FOUND));
	}
}

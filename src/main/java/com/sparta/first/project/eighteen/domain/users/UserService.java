package com.sparta.first.project.eighteen.domain.users;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sparta.first.project.eighteen.common.exception.BaseException;
import com.sparta.first.project.eighteen.domain.users.dtos.UserResponseDto;
import com.sparta.first.project.eighteen.model.users.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public UserResponseDto findUser(UUID userId) {
		Users users = userRepository.findById(userId)
			.orElseThrow(() -> new BaseException("회원을 찾을 수 없습니다.", -1, HttpStatus.NOT_FOUND));

		return UserResponseDto.from(users);
	}
}

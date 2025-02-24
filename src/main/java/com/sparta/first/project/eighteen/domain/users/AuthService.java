package com.sparta.first.project.eighteen.domain.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.first.project.eighteen.domain.users.dtos.UserRequestDto;
import com.sparta.first.project.eighteen.model.users.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void saveUser(UserRequestDto userRequestDto) {
		Users entity = userRequestDto.toEntity(passwordEncoder);
		userRepository.save(entity);
	}
}

package com.sparta.first.project.eighteen.common.security;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sparta.first.project.eighteen.common.exception.BaseException;
import com.sparta.first.project.eighteen.domain.users.UserRepository;
import com.sparta.first.project.eighteen.model.users.Users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = userRepository.findByUsername(username)
			.orElseThrow(() -> new BaseException("다시 로그인해주세요.", -1, HttpStatus.UNAUTHORIZED));

		checkUserDeleted(users);

		return new UserDetailsImpl(users);
	}

	public UserDetails loadUserByUserUUID(String userUUID) {
		Users users = userRepository.findById(UUID.fromString(userUUID))
			.orElseThrow(() -> new BaseException("다시 로그인해주세요.", -1, HttpStatus.UNAUTHORIZED));

		checkUserDeleted(users);

		return new UserDetailsImpl(users);
	}

	private void checkUserDeleted(Users users) {
		if (users.getIsDeleted()) {
			throw new BaseException("삭제된 유저입니다.", -1, HttpStatus.UNAUTHORIZED);
		}
	}
}
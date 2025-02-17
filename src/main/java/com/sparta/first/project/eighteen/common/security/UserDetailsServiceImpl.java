package com.sparta.first.project.eighteen.common.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.first.project.eighteen.common.exception.BaseException;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Map<String, User> userRepository = new HashMap<>();
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = Optional.ofNullable(userRepository.getOrDefault(username, null))
			.orElseThrow(() -> new BaseException("다시 로그인해주세요.", -1, HttpStatus.UNAUTHORIZED));

		return new UserDetailsImpl(user);
	}

	@Getter
	@AllArgsConstructor
	@Builder
	public static class User {
		private String userId;
		private String username;
		private String password;
		private Role userRole;

		public enum Role {
			CUSTOMER("ROLE_CUSTOMER"),
			OWNER("ROLE_OWNER"),
			MANAGER("ROLE_MANAGER"),
			MASTER("ROLE_MASTER");

			private final String authority;

			Role(String authority) {
				this.authority = authority;
			}

			public String getAuthority() {
				return this.authority;
			}
		}
	}

	@PostConstruct
	public void init() {
		User customer = User.builder()
			.username("customer")
			.password(passwordEncoder.encode("customer"))
			.userId("customer")
			.userRole(User.Role.CUSTOMER)
			.build();

		User owner = User.builder()
			.username("owner")
			.password(passwordEncoder.encode("owner"))
			.userId("owner")
			.userRole(User.Role.OWNER)
			.build();

		User admin = User.builder()
			.username("admin")
			.password(passwordEncoder.encode("admin"))
			.userId("admin")
			.userRole(User.Role.MANAGER)
			.build();

		userRepository.put("customer", customer);
		userRepository.put("owner", owner);
		userRepository.put("admin", admin);
	}
}
package com.sparta.first.project.eighteen.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sparta.first.project.eighteen.common.security.UserDetailsImpl;

public class AuditorAwareImpl implements AuditorAware<UUID> {

	@Override
	public Optional<UUID> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (null == authentication || !authentication.isAuthenticated()) {
			return null;
		}

		//사용자 환경에 맞게 로그인한 사용자의 정보를 불러온다.
		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

		return Optional.of(userDetails.getUsers().getUserId());
	}

}

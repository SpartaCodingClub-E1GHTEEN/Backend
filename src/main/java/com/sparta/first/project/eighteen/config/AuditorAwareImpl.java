package com.sparta.first.project.eighteen.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sparta.first.project.eighteen.common.constants.Constant;
import com.sparta.first.project.eighteen.common.security.UserDetailsImpl;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		// 회원가입과 같이 임시 권한으로 엔티티 생성할 경우 시스템 UUID로 처리
		if (authentication instanceof AnonymousAuthenticationToken) {
			return Optional.of(Constant.UserCode.SYSTEM_CODE);
		}

		//사용자 환경에 맞게 로그인한 사용자의 정보를 불러온다.
		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

		return Optional.of(userDetails.getUsers().getUserId().toString());
	}

}

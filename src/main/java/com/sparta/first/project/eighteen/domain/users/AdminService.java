package com.sparta.first.project.eighteen.domain.users;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.first.project.eighteen.common.exception.BaseException;
import com.sparta.first.project.eighteen.domain.users.dtos.AdminUserSearchRequestDto;
import com.sparta.first.project.eighteen.domain.users.dtos.AdminUserSearchResponseDto;
import com.sparta.first.project.eighteen.domain.users.dtos.AdminUserUpdateRequestDto;
import com.sparta.first.project.eighteen.model.users.Users;
import com.sparta.first.project.eighteen.utils.UserUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
	private final UserRepository userRepository;

	public PagedModel<AdminUserSearchResponseDto> findAllUsers(AdminUserSearchRequestDto requestDto) {
		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(),
			requestDto.getSort().toSort());

		Page<AdminUserSearchResponseDto> users = userRepository.searchUser(requestDto, pageable)
			.map(AdminUserSearchResponseDto::from);

		return new PagedModel<>(users);

	}

	@Transactional
	public AdminUserSearchResponseDto modifyUser(String userId, @Valid AdminUserUpdateRequestDto userRequestDto) {
		Users origin = userRepository.findById(UUID.fromString(userId))
			.orElseThrow(() -> new BaseException("유저를 찾을 수 없습니다", -1, HttpStatus.NOT_FOUND));

		// 유저의 권한 변경할 때 변경 시점 기록
		// TODO: 추후 해당 메서드에서 예외 발생 시 AOP로 기록한 아이디 취소하는 방법 고려
		if (userRequestDto.getRole() != null) {
			UserUtils.markUserModified(UUID.fromString(userId));
		}
		
		Users updated = userRequestDto.toEntity();

		origin.adminUserUpdate(updated);

		return AdminUserSearchResponseDto.from(origin);
	}

}

package com.sparta.first.project.eighteen.domain.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.first.project.eighteen.domain.users.dtos.AdminUserSearchRequestDto;
import com.sparta.first.project.eighteen.model.users.Users;

public interface UserSearchRepository {
	Page<Users> searchUser(AdminUserSearchRequestDto requestDto, Pageable pageable);
}

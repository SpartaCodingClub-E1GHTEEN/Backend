package com.sparta.first.project.eighteen.domain.users.dtos;

import com.sparta.first.project.eighteen.model.users.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminUserSearchRequestDto {
	private int page;
	private int size;
	private Role role;
}

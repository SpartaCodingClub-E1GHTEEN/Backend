package com.sparta.first.project.eighteen.domain.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSearchDto {
	private int page;
	private int size;
	private String role;
}

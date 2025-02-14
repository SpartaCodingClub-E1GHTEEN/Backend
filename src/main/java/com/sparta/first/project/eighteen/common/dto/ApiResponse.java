package com.sparta.first.project.eighteen.common.dto;

import com.sparta.first.project.eighteen.common.exception.BaseException;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse<T> {

	private int code;

	// 사용자(실제 사이트를 이용하는 유저)가 볼 데이터를 삽입
	// 성공해도 메시지를 보여줘야 하는 경우도 있고, 실패해도 보여줘야 하는 경우도 있음 (형식에 맞춰 아이디를 입력해주세요 등)
	private String message;

	private T data;

	public static <T> ApiResponse<T> ok(@Nullable String message, @Nullable T data) {
		return new ApiResponse<>(0, message, data);
	}

	public static <T> ApiResponse<T> fail(BaseException e) {
		return new ApiResponse<>(e.getCode(), e.getMessage(), null);
	}

}
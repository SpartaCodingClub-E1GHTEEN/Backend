package com.sparta.first.project.eighteen.domain.payments.dtos.naver;

import lombok.Getter;

@Getter
public class NaverPayApiResponse<T> {

	private String code;

	private String message;

	private T body;

	private Object error;

	private class Error {
		String type;
	}
}

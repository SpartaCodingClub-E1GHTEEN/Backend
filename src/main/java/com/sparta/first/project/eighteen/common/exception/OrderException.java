package com.sparta.first.project.eighteen.common.exception;

import org.springframework.http.HttpStatus;

public class OrderException {
	public static class OrderNotFound extends BaseException {
		public OrderNotFound(String message, int code, HttpStatus status) {
			super(message, code, status);
		}
	}
}

package com.sparta.first.project.eighteen.common.exception;

import org.springframework.http.HttpStatus;

public class OrderException {
	public static class OrderNotFound extends BaseException {
		public OrderNotFound() {
			super("주문 정보가 없습니다", -403, HttpStatus.BAD_REQUEST);
		}
	}
}

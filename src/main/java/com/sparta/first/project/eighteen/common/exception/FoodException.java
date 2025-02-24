package com.sparta.first.project.eighteen.common.exception;

import org.springframework.http.HttpStatus;

public class FoodException {
	public static class FoodNotFound extends BaseException {
		public FoodNotFound() {
			super("메뉴 정보가 없습니다", -403, HttpStatus.BAD_REQUEST);
		}
	}

	public static class FoodOptionNotFound extends BaseException {
		public FoodOptionNotFound() {
			super("메뉴 옵션 정보가 없습니다", -403, HttpStatus.BAD_REQUEST);
		}
	}
}

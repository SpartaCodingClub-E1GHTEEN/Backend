package com.sparta.first.project.eighteen.common.exception;

import org.springframework.http.HttpStatus;

public class UserException {
	public static class UserNotFound extends BaseException {
		public UserNotFound() {
			super("사용자 정보가 없습니다", -402, HttpStatus.BAD_REQUEST);
		}
	}
}

package com.sparta.first.project.eighteen.common.exception;

import org.springframework.http.HttpStatus;

public class StoreException {
	public static class StoreNotFound extends BaseException {
		public StoreNotFound() {
			super("식당 정보가 없습니다", -401, HttpStatus.BAD_REQUEST);
		}
	}
}

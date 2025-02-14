package com.sparta.first.project.eighteen.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = {BaseException.class})
	public ResponseEntity<ApiResponse<BaseException>> errorResponse(BaseException exception) {
		log.error("ERROR] 코드 ={}, 메시지 ={}", exception.getCode(), exception.getMessage());
		return ResponseEntity.status(exception.getStatus()) // 400, 401, 403
			.body(ApiResponse.fail(exception));
	}
}

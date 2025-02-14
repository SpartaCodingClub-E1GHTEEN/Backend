package com.sparta.first.project.eighteen.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
	private String message;
	private int code;
	private HttpStatus status;

	public BaseException(String message, int code, HttpStatus status){
		super(message);
		this.message = message;
		this.code = code;
		this.status = status;
	}
}
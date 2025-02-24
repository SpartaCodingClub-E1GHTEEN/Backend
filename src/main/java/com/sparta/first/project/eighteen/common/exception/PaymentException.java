package com.sparta.first.project.eighteen.common.exception;

import org.springframework.http.HttpStatus;

public class PaymentException {
	public static class PaymentNotFound extends BaseException {
		public PaymentNotFound() {
			super("결제 정보가 없습니다", -601, HttpStatus.BAD_REQUEST);
		}
	}

	public static class InvalidPaymentMethod extends BaseException {
		public InvalidPaymentMethod() {
			super("결제 수단이 잘못 되었습니다. ", -602, HttpStatus.BAD_REQUEST);
		}
	}

	public static class NaverPayReservationFailed extends BaseException {
		public NaverPayReservationFailed(String message) {
			super("결제 예약에 실패했습니다. " + message, -603, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

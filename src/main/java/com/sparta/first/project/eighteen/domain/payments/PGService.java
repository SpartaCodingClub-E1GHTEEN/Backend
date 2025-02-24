package com.sparta.first.project.eighteen.domain.payments;

import org.springframework.stereotype.Component;

import com.sparta.first.project.eighteen.common.exception.PaymentException;
import com.sparta.first.project.eighteen.domain.payments.dtos.naver.NaverPayReservationRequestDto;
import com.sparta.first.project.eighteen.model.orders.Orders;
import com.sparta.first.project.eighteen.model.payments.PaymentMethod;
import com.sparta.first.project.eighteen.utils.NaverPayApiClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PGService {
	private final NaverPayApiClient naverPayApiClient;

	public String requestPayment(Orders order, PaymentMethod paymentMethod) {

		switch (paymentMethod) {
			case NAVER_PAY -> {
				return naverPayApiClient.requestReservationId(NaverPayReservationRequestDto.fromOrders(order));
			}
		}

		throw new PaymentException.InvalidPaymentMethod();
	}
}

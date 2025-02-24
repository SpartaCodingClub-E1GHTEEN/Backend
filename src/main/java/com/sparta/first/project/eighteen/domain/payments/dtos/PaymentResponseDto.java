package com.sparta.first.project.eighteen.domain.payments.dtos;

import com.sparta.first.project.eighteen.model.payments.PaymentMethod;
import com.sparta.first.project.eighteen.model.payments.Payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponseDto {

	String paymentId;

	String orderId;

	int amount;

	PaymentMethod paymentMethod;

	public static PaymentResponseDto fromEntity(Payments payment) {
		return PaymentResponseDto.builder()
			.paymentId(payment.getId().toString())
			.orderId(payment.getOrderId().toString())
			.amount(payment.getAmount())
			.paymentMethod(payment.getPaymentMethod())
			.build();
	}
}

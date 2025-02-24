package com.sparta.first.project.eighteen.domain.payments.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sparta.first.project.eighteen.model.payments.PaymentMethod;
import com.sparta.first.project.eighteen.model.payments.PaymentStatus;
import com.sparta.first.project.eighteen.model.payments.Payments;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentCreateRequestDto {

	@NotBlank
	private String orderId;

	@NotNull
	private PaymentMethod paymentMethod;

	@NotBlank
	private String pgPaymentId;

	@Min(10)
	private int amount;

	@NotNull
	private PaymentStatus status;

	public Payments toEntity() {
		return Payments.builder()
			.orderId(UUID.fromString(orderId))
			.status(PaymentStatus.COMPLETED)
			.pgPaymentId(this.pgPaymentId)
			.paymentTime(LocalDateTime.now())
			.paymentMethod(this.paymentMethod)
			.amount(this.amount)
			.build();
	}
}

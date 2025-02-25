package com.sparta.first.project.eighteen.domain.payments;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.first.project.eighteen.common.exception.OrderException;
import com.sparta.first.project.eighteen.common.exception.PaymentException;
import com.sparta.first.project.eighteen.domain.orders.OrdersRepository;
import com.sparta.first.project.eighteen.domain.payments.dtos.PaymentCreateRequestDto;
import com.sparta.first.project.eighteen.domain.payments.dtos.PaymentResponseDto;
import com.sparta.first.project.eighteen.model.orders.Orders;
import com.sparta.first.project.eighteen.model.payments.PaymentMethod;
import com.sparta.first.project.eighteen.model.payments.PaymentStatus;
import com.sparta.first.project.eighteen.model.payments.Payments;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class PaymentService {

	private final PaymentsRepository paymentsRepository;
	private final OrdersRepository ordersRepository;
	private final PGService pgService;

	public PaymentResponseDto createPayment(PaymentCreateRequestDto requestDto) {
		Orders orders = ordersRepository.findByIdAndIsDeletedIsFalse(UUID.fromString(requestDto.getOrderId()))
			.orElseThrow(OrderException.OrderNotFound::new);

		Payments payment = paymentsRepository.save(requestDto.toEntity());

		return PaymentResponseDto.fromEntity(payment);
	}

	public String requestPayment(String orderId, PaymentMethod paymentMethod) {
		Orders orders = ordersRepository.findByIdAndIsDeletedIsFalse(UUID.fromString(orderId))
			.orElseThrow(OrderException.OrderNotFound::new);

		return pgService.requestPayment(orders, paymentMethod);
	}

	@Transactional(readOnly = true)
	public PaymentResponseDto readPayment(String id) {
		Payments payment = paymentsRepository.findByIdAndIsDeletedIsFalse(UUID.fromString(id))
			.orElseThrow(PaymentException.PaymentNotFound::new);

		return PaymentResponseDto.fromEntity(payment);
	}

	public PaymentResponseDto updatePayment(String id, PaymentStatus status) {
		Payments payment = paymentsRepository.findByIdAndIsDeletedIsFalse(UUID.fromString(id))
			.orElseThrow(PaymentException.PaymentNotFound::new);

		payment.updateStatus(status);

		return PaymentResponseDto.fromEntity(payment);
	}

	public void deletePayment(String id, String userId) {
		Payments payment = paymentsRepository.findByIdAndIsDeletedIsFalse(UUID.fromString(id))
			.orElseThrow(PaymentException.PaymentNotFound::new);

		payment.delete(true, userId);
	}
}

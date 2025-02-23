package com.sparta.first.project.eighteen.domain.payments;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sparta.first.project.eighteen.common.exception.OrderException;
import com.sparta.first.project.eighteen.domain.orders.OrdersRepository;
import com.sparta.first.project.eighteen.domain.payments.dtos.PaymentCreateRequestDto;
import com.sparta.first.project.eighteen.domain.payments.dtos.PaymentResponseDto;
import com.sparta.first.project.eighteen.model.orders.Orders;
import com.sparta.first.project.eighteen.model.payments.PaymentMethod;
import com.sparta.first.project.eighteen.model.payments.Payments;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentService {

	private final PaymentsRepository paymentsRepository;
	private final OrdersRepository ordersRepository;
	private final PGService pgService;

	public PaymentResponseDto createPayment(PaymentCreateRequestDto requestDto) {
		Orders orders = ordersRepository.findById(UUID.fromString(requestDto.getOrderId()))
			.orElseThrow(OrderException.OrderNotFound::new);

		Payments payment = paymentsRepository.save(requestDto.toEntity());

		return PaymentResponseDto.fromEntity(payment);
	}

	public String requestPayment(String orderId, PaymentMethod paymentMethod) {
		Orders orders = ordersRepository.findById(UUID.fromString(orderId))
			.orElseThrow(OrderException.OrderNotFound::new);

		return pgService.requestPayment(orders, paymentMethod);
	}
}

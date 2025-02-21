package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.sparta.first.project.eighteen.model.orders.OrderDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsResponseDto {
	private String orderDetailsId;

	private String productId;

	private String productName;

	private int amount;

	private List<OrderDetailsOptionResponseDto> options;

	public OrderDetailsResponseDto(OrderDetails orderDetails) {
		this.orderDetailsId = orderDetails.getId().toString();
		this.productId = orderDetails.getFood().getId().toString();
		this.productName = orderDetails.getFoodName();
		this.amount = orderDetails.getAmount();
		this.options = orderDetails.getOrderDetailsOptions()
			.stream()
			.map(orderDetailsOptions -> new OrderDetailsOptionResponseDto(orderDetailsOptions))
			.collect(Collectors.toList()
			);
	}
}

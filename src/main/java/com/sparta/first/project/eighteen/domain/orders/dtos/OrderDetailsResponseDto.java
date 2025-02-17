package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

	//TODO: DELETE MOCKDATA
	public OrderDetailsResponseDto(OrderDetailsRequestDto requestDto) {
		this.orderDetailsId = orderDetailsId;
		this.productId = requestDto.getProductId();
		this.productName = "짜장면";
		this.amount = 1000;
		this.options = Arrays.asList(requestDto.getOptionIds()).stream()
			.map(option -> new OrderDetailsOptionResponseDto(option))
			.collect(Collectors.toList());
	}

	//TODO: DELETE MOCKDATA
	public OrderDetailsResponseDto(OrderDetailsUpdateRequestDto requestDto) {
		this.orderDetailsId = orderDetailsId;
		this.productId = requestDto.getProductId();
		this.productName = "짜장면";
		this.amount = 1000;
		this.options = Arrays.asList(requestDto.getOptionIds()).stream()
			.map(option -> new OrderDetailsOptionResponseDto(option))
			.collect(Collectors.toList());
	}
}

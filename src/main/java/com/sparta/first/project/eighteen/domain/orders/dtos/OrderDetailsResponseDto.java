package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsResponseDto {
	private String orderDetailsId;
	private String productId;
	private String productName;
	private int amount;
	private String[] options;

	// mock 데이터를 위한 임시 생성자
	public OrderDetailsResponseDto(OrderDetailsRequestDto requestDto) {
		this.orderDetailsId = orderDetailsId;
		this.productId = requestDto.getProductId();
		this.productName = "짜장면";
		this.amount = requestDto.getAmount();
		this.options = Arrays.asList(requestDto.getOptions()).stream()
			.map(option -> "옵션"+ option)
			.toArray(String[]::new);

	}
}

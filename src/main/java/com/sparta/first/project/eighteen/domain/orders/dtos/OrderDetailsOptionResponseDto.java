package com.sparta.first.project.eighteen.domain.orders.dtos;

import com.sparta.first.project.eighteen.model.orders.OrderDetailsOptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailsOptionResponseDto {
	
	private String optionId;

	private String optionName;

	private int price;

	public OrderDetailsOptionResponseDto(OrderDetailsOptions options) {
		this.optionId = options.getId().toString();
		this.optionName = options.getOptionName();
		this.price = options.getOptionPrice();
	}
}

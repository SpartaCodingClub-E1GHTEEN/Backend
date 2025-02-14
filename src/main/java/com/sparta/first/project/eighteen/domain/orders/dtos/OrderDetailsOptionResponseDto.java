package com.sparta.first.project.eighteen.domain.orders.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailsOptionResponseDto {
	private String optionId;
	private String optionName;
	private int price;

	//TODO: DELETE MOCKDATA
	public OrderDetailsOptionResponseDto(String optionId) {
		this.optionId = optionId;
		this.optionName = "옵션" + optionId;
		this.price = 100;
	}
}

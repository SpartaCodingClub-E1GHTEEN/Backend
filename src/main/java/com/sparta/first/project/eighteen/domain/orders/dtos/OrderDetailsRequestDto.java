package com.sparta.first.project.eighteen.domain.orders.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsRequestDto {
	private String productId;
	private int amount;
	private String[] options;
}


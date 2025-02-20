package com.sparta.first.project.eighteen.domain.orders.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailsUpdateRequestDto {

	@NotBlank
	private String orderDetailsId;

	private String productId;

	private int amount;

	private String[] optionIds;
}

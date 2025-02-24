package com.sparta.first.project.eighteen.domain.orders.dtos;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderUpdateRequestDto {

	String noteToStore;

	String noteToDelivery;

	@Min(10)
	int totalPrice;

}

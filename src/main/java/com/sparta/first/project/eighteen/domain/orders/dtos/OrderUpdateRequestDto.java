package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderUpdateRequestDto {

	@NotBlank
	String status;

	String noteToStore;

	String noteToDelivery;

	@NotNull
	List<OrderDetailsUpdateRequestDto> orderDetails;
}

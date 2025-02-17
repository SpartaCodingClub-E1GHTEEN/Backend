package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderUpdateRequestDto {
	String status;
	String noteToStore;
	String noteToDelivery;
	List<OrderDetailsUpdateRequestDto> orderDetails;
}

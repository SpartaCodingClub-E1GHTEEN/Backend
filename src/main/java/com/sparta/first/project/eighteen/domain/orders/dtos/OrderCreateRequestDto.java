package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateRequestDto {
	private String storeId;
	private Boolean isStoreOrder;
	private String noteToStore;
	private String noteToDelivery;
	private List<OrderDetailsRequestDto> orderDetails;

	//TODO: DELETE MOCKDATA
	public OrderCreateRequestDto(String id) {
		this.storeId = "1";
		this.isStoreOrder = false;
		this.noteToStore = "맛있게 만들어주세요";
		this.noteToDelivery = "천천히 오세요";
		this.orderDetails = new ArrayList<OrderDetailsRequestDto>();
	}
}

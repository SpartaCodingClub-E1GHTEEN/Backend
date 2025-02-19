package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.sparta.first.project.eighteen.model.orders.OrderStatus;
import com.sparta.first.project.eighteen.model.orders.Orders;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderCreateRequestDto {
	private String storeId;
	private Boolean isStoreOrder;
	private String noteToStore;
	private String noteToDelivery;
	private int totalPrice;
	private List<OrderDetailsRequestDto> orderDetails;

	public Orders toEntity(Stores store, Users user) {
		return Orders.builder()
			.store(store)
			.user(user)
			.orderTime(LocalDateTime.now())
			.isStoreOrder(this.isStoreOrder)
			.status(OrderStatus.PENDING)
			.noteToStore(this.noteToStore)
			.noteToDelivery(this.noteToDelivery)
			.totalPrice(this.totalPrice)
			.build();
	}
}

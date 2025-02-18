package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.sparta.first.project.eighteen.model.orders.OrderStatus;
import com.sparta.first.project.eighteen.model.orders.Orders;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class OrderResponseDto {
	String id;
	String storeId;
	String storeName;
	String userId;
	String userName;
	LocalDateTime orderTime;
	boolean isStoreOrder;
	OrderStatus status;
	String noteToStore;
	String noteToDelivery;
	List<OrderDetailsResponseDto> orderDetails;

	public static OrderResponseDto fromEntity(Orders orders) {
		return OrderResponseDto.builder()
			.id(orders.getId().toString())
			.storeId(orders.getStore().getId().toString())
			.storeName(orders.getStore().getStoreName())
			.userId(orders.getUser().getUserId().toString())
			.userName(orders.getUser().getUsername())
			.orderTime(orders.getOrderTime())
			.isStoreOrder(orders.isStoreOrder())
			.status(orders.getStatus())
			.noteToStore(orders.getNoteToStore())
			.noteToDelivery(orders.getNoteToDelivery())
			.orderDetails(
				orders.getOrderDetails()
					.stream()
					.map(orderdetail -> new OrderDetailsResponseDto(orderdetail))
					.collect(Collectors.toList())
			)
			.build();
	}
}
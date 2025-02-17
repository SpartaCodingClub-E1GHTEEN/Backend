package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.sparta.first.project.eighteen.model.orders.OrderStatus;

import lombok.Getter;
import lombok.Setter;

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

	//TODO: DELETE MOCKDATA
	public OrderResponseDto(OrderCreateRequestDto requestDto) {
		this.id = "1";
		this.storeId = requestDto.getStoreId();
		this.storeName = "콩콩반점";
		this.userId = "user1";
		this.userName = "김철수";
		this.orderTime = LocalDateTime.now();
		this.isStoreOrder = false;
		this.status = OrderStatus.PENDING;
		this.noteToStore = requestDto.getNoteToStore();
		this.noteToDelivery = requestDto.getNoteToDelivery();
		this.orderDetails = requestDto.getOrderDetails().stream()
			.map(orderDetail -> new OrderDetailsResponseDto(orderDetail))
			.collect(Collectors.toList());
	}

	//TODO: DELETE MOCKDATA
	public OrderResponseDto(OrderUpdateRequestDto requestDto) {
		this.storeId = "1";
		this.storeName = "콩콩반점";
		this.userId = "user1";
		this.userName = "김철수";
		this.orderTime = LocalDateTime.now();
		this.isStoreOrder = false;
		this.status = OrderStatus.PENDING;
		this.noteToStore = requestDto.getNoteToStore();
		this.noteToDelivery = requestDto.getNoteToDelivery();
		this.orderDetails = requestDto.getOrderDetails().stream()
			.map(orderDetail -> new OrderDetailsResponseDto(orderDetail))
			.collect(Collectors.toList());
	}
}
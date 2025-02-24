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
	private String id;

	private String storeId;

	private String storeName;

	private String userId;

	private String userName;

	private LocalDateTime orderTime;

	private boolean isStoreOrder;

	private OrderStatus status;

	private String noteToStore;

	private String noteToDelivery;

	private int totalPrice;

	private int totalCount;

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
			.totalPrice(orders.getTotalPrice())
			.totalCount(orders.getTotalCount())
			.orderDetails(
				orders.getOrderDetails()
					.stream()
					.map(orderdetail -> new OrderDetailsResponseDto(orderdetail))
					.collect(Collectors.toList())
			)
			.build();
	}

	//JPA criteria에서 객체 변환용 생성자
	public OrderResponseDto(Orders orders) {
		this.id = orders.getId().toString();
		this.storeId = orders.getStore().getId().toString();
		this.storeName = orders.getStore().getStoreName();
		this.userId = orders.getUser().getUserId().toString();
		this.userName = orders.getUser().getUsername();
		this.orderTime = orders.getOrderTime();
		this.isStoreOrder = orders.isStoreOrder();
		this.status = orders.getStatus();
		this.noteToStore = orders.getNoteToStore();
		this.noteToDelivery = orders.getNoteToDelivery();
		this.orderDetails = orders.getOrderDetails()
			.stream()
			.map(orderdetail -> new OrderDetailsResponseDto(orderdetail))
			.collect(Collectors.toList());
	}
}
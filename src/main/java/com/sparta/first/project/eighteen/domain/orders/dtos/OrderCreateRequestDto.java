package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.sparta.first.project.eighteen.common.constants.Constant;
import com.sparta.first.project.eighteen.model.orders.OrderStatus;
import com.sparta.first.project.eighteen.model.orders.Orders;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderCreateRequestDto {

	@NotBlank
	private String storeId;

	@NotNull
	private Boolean isStoreOrder;

	@Size(max = 50, message = Constant.ErrorMessage.NOTE_IS_TOO_LONG)
	private String noteToStore;

	@Size(max = 50, message = Constant.ErrorMessage.NOTE_IS_TOO_LONG)
	private String noteToDelivery;

	@Min(100)
	private int totalPrice;

	@Min(1)
	private int totalCount;

	@NotNull
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
			.totalCount(this.totalCount)
			.build();
	}
}

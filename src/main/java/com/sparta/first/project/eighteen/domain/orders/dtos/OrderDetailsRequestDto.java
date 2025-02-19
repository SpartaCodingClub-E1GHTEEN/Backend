package com.sparta.first.project.eighteen.domain.orders.dtos;

import com.sparta.first.project.eighteen.model.foods.Foods;
import com.sparta.first.project.eighteen.model.orders.OrderDetails;
import com.sparta.first.project.eighteen.model.orders.Orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsRequestDto {
	private String productId;
	private String[] optionIds;
	private int amount;

	public OrderDetails toEntity(Orders order, Foods food, int amount) {
		return OrderDetails.builder()
			.order(order)
			.food(food)
			.amount(amount)
			.foodName(food.getFoodName())
			.foodPrice(food.getFoodPrice())
			.build();
	}
}


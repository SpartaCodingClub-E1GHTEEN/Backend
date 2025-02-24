package com.sparta.first.project.eighteen.domain.orders;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.first.project.eighteen.common.exception.FoodException;
import com.sparta.first.project.eighteen.common.exception.OrderException;
import com.sparta.first.project.eighteen.common.exception.StoreException;
import com.sparta.first.project.eighteen.common.exception.UserException;
import com.sparta.first.project.eighteen.domain.foods.FoodsRepository;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderCreateRequestDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderDetailsRequestDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderResponseDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderSearchRequestDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderUpdateRequestDto;
import com.sparta.first.project.eighteen.domain.stores.StoreRepository;
import com.sparta.first.project.eighteen.domain.users.UserRepository;
import com.sparta.first.project.eighteen.model.foods.FoodOptions;
import com.sparta.first.project.eighteen.model.foods.Foods;
import com.sparta.first.project.eighteen.model.orders.OrderDetails;
import com.sparta.first.project.eighteen.model.orders.OrderDetailsOptions;
import com.sparta.first.project.eighteen.model.orders.OrderStatus;
import com.sparta.first.project.eighteen.model.orders.Orders;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Users;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrdersRepository ordersRepository;
	private final OrderDetailsRepository orderDetailsRepository;
	private final OrderDetailsOptionsRepository orderDetailsOptionsRepository;
	private final StoreRepository storeRepository;
	private final UserRepository userRepository;
	private final FoodsRepository foodsRepository;

	public String createOrder(OrderCreateRequestDto requestDto, UUID userId) {
		Stores store = storeRepository.findById(UUID.fromString(requestDto.getStoreId()))
			.orElseThrow(() -> new StoreException.StoreNotFound());

		Users customer = userRepository.findById(userId)
			.orElseThrow(() -> new UserException.UserNotFound());

		Orders order = ordersRepository.save(requestDto.toEntity(store, customer));

		for (OrderDetailsRequestDto orderDetail : requestDto.getOrderDetails()) {
			String id = orderDetail.getProductId();
			UUID uuid = UUID.fromString(id);
			Foods food = foodsRepository.findById(UUID.fromString(orderDetail.getProductId()))
				.orElseThrow(() -> new FoodException.FoodNotFound());

			OrderDetails orderDetailsEntity = orderDetailsRepository.save(
				orderDetail.toEntity(order, food, orderDetail.getAmount()));

			for (String optionId : orderDetail.getOptionIds()) {
				FoodOptions foodOption = food.getFoodOptions().stream()
					.filter(option -> option.getId().equals(UUID.fromString(optionId)))
					.findFirst()
					.orElseThrow(() -> new FoodException.FoodOptionNotFound());
				OrderDetailsOptions orderDetailsOptions = OrderDetailsOptions.builder()
					.orderDetail(orderDetailsEntity)
					.foodOption(foodOption)
					.optionName(foodOption.getOptionName())
					.optionPrice(foodOption.getOptionPrice())
					.build();
				orderDetailsOptionsRepository.save(orderDetailsOptions);
			}
		}

		return order.getId().toString();
	}

	@Transactional(readOnly = true)
	public OrderResponseDto readOrder(String id) {
		Orders orders = ordersRepository.findById(UUID.fromString(id))
			.orElseThrow(() -> new OrderException.OrderNotFound());

		return OrderResponseDto.fromEntity(orders);
	}

	@Transactional(readOnly = true)
	public PagedModel<OrderResponseDto> searchOrder(OrderSearchRequestDto requestDto) {
		Page<OrderResponseDto> orders = ordersRepository.findAllBySearchParam(requestDto);

		return new PagedModel<>(orders);
	}

	@Transactional
	public OrderResponseDto updateOrder(OrderUpdateRequestDto requestDto, String id) {
		Orders orders = ordersRepository.findById(UUID.fromString(id))
			.orElseThrow(() -> new OrderException.OrderNotFound());

		orders.update(requestDto);
		
		return OrderResponseDto.fromEntity(orders);
	}

	@Transactional
	public OrderResponseDto cancelOrder(String id) {
		Orders orders = ordersRepository.findById(UUID.fromString(id))
			.orElseThrow(() -> new OrderException.OrderNotFound());

		orders.cancel();

		return OrderResponseDto.fromEntity(orders);
	}

	public void deleteOrder(String id, String userId) {
		Orders orders = ordersRepository.findById(UUID.fromString(id))
			.orElseThrow(() -> new OrderException.OrderNotFound());

		orders.delete(true, userId);
	}

	public OrderResponseDto updateOrderStatus(String id, OrderStatus status) {
		Orders orders = ordersRepository.findById(UUID.fromString(id))
			.orElseThrow(() -> new OrderException.OrderNotFound());

		orders.changeStatus(status);
		return OrderResponseDto.fromEntity(orders);
	}
}

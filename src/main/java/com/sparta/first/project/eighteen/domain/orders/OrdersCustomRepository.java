package com.sparta.first.project.eighteen.domain.orders;

import org.springframework.data.domain.Page;

import com.sparta.first.project.eighteen.domain.orders.dtos.OrderResponseDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderSearchRequestDto;

public interface OrdersCustomRepository {
	Page<OrderResponseDto> findAllBySearchParam(OrderSearchRequestDto requestDto);
}

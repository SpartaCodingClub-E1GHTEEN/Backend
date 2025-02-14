package com.sparta.first.project.eighteen.domain.orders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderRequestDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderResponseDto;

@RequestMapping("/api/v1/order")
@RestController
public class OrderController {

	@PostMapping
	public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@RequestBody OrderRequestDto requestDto) {
		return ResponseEntity.ok(ApiResponse.ok("메시지", new OrderResponseDto(requestDto)));
	}

}
package com.sparta.first.project.eighteen.domain.orders;

import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderCreateRequestDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderResponseDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderSearchRequestDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderUpdateRequestDto;
import com.sparta.first.project.eighteen.model.orders.OrderStatus;

@RequestMapping("/api/v1/order")
@RestController
public class OrderController {

	@PostMapping
	public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@RequestBody OrderCreateRequestDto requestDto) {
		return ResponseEntity.ok(ApiResponse.ok("주문이 완료되었습니다.", new OrderResponseDto(requestDto)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<OrderResponseDto>> readOrder(@PathVariable String id) {
		return ResponseEntity.ok(ApiResponse.ok("주문을 조회했습니다.", new OrderResponseDto(new OrderCreateRequestDto(id))));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<PagedModel<OrderResponseDto>>> searchOrder(
		@ModelAttribute OrderSearchRequestDto requestDto) {
		return ResponseEntity.ok(ApiResponse.ok("주문 목록을 조회했습니다", null));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(@PathVariable String id,
		@RequestBody OrderUpdateRequestDto requestDto) {
		OrderResponseDto responseDto = new OrderResponseDto(requestDto);
		responseDto.setId(id);
		return ResponseEntity.ok(ApiResponse.ok("주문 목록을 조회했습니다", responseDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<OrderResponseDto>> cancelOrder(@PathVariable String id) {
		OrderResponseDto responseDto = new OrderResponseDto(new OrderCreateRequestDto(id));
		responseDto.setStatus(OrderStatus.CANCELED);
		return ResponseEntity.ok(ApiResponse.ok("주문 목록을 조회했습니다", responseDto));
	}
}
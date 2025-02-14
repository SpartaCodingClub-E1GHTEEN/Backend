package com.sparta.first.project.eighteen.domain.orders;

import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderRequestDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderResponseDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderSearchRequestDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderUpdateRequestDto;
import com.sparta.first.project.eighteen.model.orders.OrderStatus;

@RequestMapping("/api/v1/order")
@RestController
public class OrderController {

	@PostMapping
	public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@RequestBody OrderRequestDto requestDto) {
		return ResponseEntity.ok(ApiResponse.ok("메시지", new OrderResponseDto(requestDto)));
	}

	@GetMapping("/{id}")
	public OrderResponseDto getOrder(@PathVariable String id){
		return new OrderResponseDto(new OrderRequestDto(id));
	}

	@GetMapping
	public PagedModel<OrderResponseDto> getOrder(@ModelAttribute OrderSearchRequestDto requestDto){
		return null;
	}

	@PatchMapping("/{id}")
	public OrderResponseDto updateOrder(@PathVariable String id, @RequestBody OrderUpdateRequestDto requestDto){
		OrderResponseDto responseDto = new OrderResponseDto(requestDto);
		responseDto.setId(id);
		return responseDto;
	}

	@DeleteMapping("/{id}")
	public OrderResponseDto cancelOrder(@PathVariable String id){
		OrderResponseDto responseDTO = new OrderResponseDto(new OrderRequestDto(id));
		responseDTO.setStatus(OrderStatus.CANCELED);
		return responseDTO;
	}

}
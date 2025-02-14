package com.sparta.first.project.eighteen.domain.foods;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodCreateRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodCreateResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodSearchRequestDto;

@RestController
@RequestMapping("/api/v1")
public class FoodsController {
	private FoodsService foodsService;

	@PostMapping("/food")
	public ResponseEntity<FoodCreateResponseDto> createFood(@RequestBody FoodCreateRequestDto requestDto) {

		return null;
	}

	@GetMapping("/store/{storeId}/food")
	public ResponseEntity<ApiResponse<FoodResponseDto>> searchFood(@PathVariable String storeId,
		@RequestParam(required = false) String keyword, @RequestParam(required = false) String filter,
		@RequestParam(required = false) String sort, @RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int limit) {

		// DTO로 변환하여 서비스에 전달
		FoodSearchRequestDto searchDto = new FoodSearchRequestDto(keyword, filter, sort, page, limit);
		FoodResponseDto responseDto = null;

		return null;
	}
}

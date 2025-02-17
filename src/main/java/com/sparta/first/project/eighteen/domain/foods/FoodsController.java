package com.sparta.first.project.eighteen.domain.foods;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodCreateRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodGetResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodSearchRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodUpdateRequestDto;

@RestController
@RequestMapping("/api/v1")
public class FoodsController {
	private FoodsService foodsService;

	@PostMapping("/foods")
	public ResponseEntity<ApiResponse<FoodResponseDto>> createFood(@RequestBody FoodCreateRequestDto requestDto) {

		return null;
	}

	@GetMapping("/stores/{storeId}/foods")
	public ResponseEntity<ApiResponse<FoodGetResponseDto>> searchFood(@PathVariable String storeId,
		@ModelAttribute FoodSearchRequestDto requestDto) {

		FoodGetResponseDto responseDto = null;

		return null;
	}

	@GetMapping("/foods/{foodId}")
	public ResponseEntity<ApiResponse<FoodGetResponseDto>> getFood(@PathVariable String foodId) {

		return null;
	}

	@PutMapping("/foods/{foodId}")
	public ResponseEntity<ApiResponse<FoodResponseDto>> updateFood(@PathVariable String foodId,
		@RequestBody FoodUpdateRequestDto requestDto) {

		return null;
	}

	@DeleteMapping("/foods/{foodId}")
	public ResponseEntity<ApiResponse<Void>> deleteFood(@PathVariable String foodId) {

		return null;
	}
}

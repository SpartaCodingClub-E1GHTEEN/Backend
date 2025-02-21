package com.sparta.first.project.eighteen.domain.foods;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodSingleResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodUpdateRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "FoodsController")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FoodsController {
	private final FoodsService foodsService;

	// USER_ROLE -> OWNER
	@PreAuthorize("hasAnyRole('Master', 'MANAGER', 'OWNER')")
	@PostMapping("/foods")
	public ResponseEntity<ApiResponse<FoodResponseDto>> createFood(@RequestBody FoodCreateRequestDto requestDto) {

		FoodResponseDto responseDto = foodsService.createFood(requestDto);

		return ResponseEntity.ok(ApiResponse.ok("메뉴가 성공적으로 생성되었습니다.", responseDto));
	}

	// USER_ROLE -> ANYONE
	@GetMapping("/stores/{storeId}/foods")
	public ResponseEntity<ApiResponse<FoodGetResponseDto>> searchFood(@PathVariable UUID storeId,
		@ModelAttribute FoodSearchRequestDto requestDto) {

		FoodGetResponseDto responseDto = foodsService.searchFood(storeId, requestDto);

		return ResponseEntity.ok(ApiResponse.ok("메뉴 검색 결과", responseDto));
	}

	// USER_ROLE -> ANYONE
	@GetMapping("/foods/{foodId}")
	public ResponseEntity<ApiResponse<FoodSingleResponseDto>> getFood(@PathVariable UUID foodId) {

		FoodSingleResponseDto responseDto = foodsService.getFood(foodId);

		return ResponseEntity.ok(ApiResponse.ok("메뉴를 성공적으로 조회했습니다.", responseDto));
	}

	// USER_ROLE -> OWNER
	@PreAuthorize("hasAnyRole('Master', 'MANAGER', 'OWNER')")
	@PutMapping("/foods/{foodId}")
	public ResponseEntity<ApiResponse<FoodResponseDto>> updateFood(@PathVariable String foodId,
		@RequestBody FoodUpdateRequestDto requestDto) {

		return null;
	}

	// USER_ROLE -> OWNER
	@PreAuthorize("hasAnyRole('Master', 'MANAGER', 'OWNER')")
	@DeleteMapping("/foods/{foodId}")
	public ResponseEntity<ApiResponse<Void>> deleteFood(@PathVariable UUID foodId) {

		foodsService.deleteFood(foodId);

		return ResponseEntity.ok(ApiResponse.ok("메뉴를 성공적으로 삭제했습니다.", null));
	}
}

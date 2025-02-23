package com.sparta.first.project.eighteen.domain.foods;

import java.util.List;
import java.util.UUID;

import org.springframework.data.web.PagedModel;
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
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodOptionRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodOptionResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodSearchRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodUpdateRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "FoodsController")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FoodsController {
	private final FoodsService foodsService;

	/**
	 * 메뉴 생성
	 *
	 * @param requestDto : 메뉴 정보
	 * @return
	 */
	// USER_ROLE -> OWNER
	@PreAuthorize("hasAnyRole('Master', 'MANAGER', 'OWNER')")
	@PostMapping("/foods")
	public ResponseEntity<ApiResponse<FoodResponseDto>> createFood(@RequestBody FoodCreateRequestDto requestDto) {

		FoodResponseDto responseDto = foodsService.createFood(requestDto);

		return ResponseEntity.ok(ApiResponse.ok("메뉴가 성공적으로 생성되었습니다.", responseDto));
	}

	/**
	 * 메뉴 검색
	 *
	 * @param storeId : 메뉴가 있는 가게 ID
	 * @param requestDto : 검색 조건
	 * @return : 메뉴에 있는 메뉴 정보
	 */
	// USER_ROLE -> ANYONE
	@GetMapping("/stores/{storeId}/foods")
	public ResponseEntity<ApiResponse<PagedModel<FoodResponseDto>>> searchFood(
		@PathVariable UUID storeId,
		@ModelAttribute FoodSearchRequestDto requestDto) {

		PagedModel<FoodResponseDto> responseDto = foodsService.searchFood(storeId, requestDto);

		return ResponseEntity.ok(ApiResponse.ok("메뉴 검색 결과", responseDto));
	}

	/**
	 * 특정 메뉴 조회
	 *
	 * @param foodId : 조회할 메뉴 ID
	 * @return : 조회할 메뉴 정보
	 */
	// USER_ROLE -> ANYONE
	@GetMapping("/foods/{foodId}")
	public ResponseEntity<ApiResponse<FoodResponseDto>> getFood(@PathVariable UUID foodId) {

		FoodResponseDto responseDto = foodsService.getFood(foodId);

		return ResponseEntity.ok(ApiResponse.ok("메뉴를 성공적으로 조회했습니다.", responseDto));
	}

	/**
	 * 메뉴 수정
	 *
	 * @param foodId : 수정할 메뉴 ID
	 * @param requestDto : 수정할 메뉴 정보
	 * @return
	 */
	// USER_ROLE -> OWNER
	@PreAuthorize("hasAnyRole('Master', 'MANAGER', 'OWNER')")
	@PutMapping("/foods/{foodId}")
	public ResponseEntity<ApiResponse<FoodResponseDto>> updateFood(@PathVariable UUID foodId,
		@RequestBody FoodUpdateRequestDto requestDto) {

		FoodResponseDto responseDto = foodsService.updateFood(foodId, requestDto);

		return ResponseEntity.ok(ApiResponse.ok("메뉴를 성공적으로 수정했습니다.", responseDto));
	}

	/**
	 * 메뉴 삭제
	 *
	 * @param foodId : 삭제할 메뉴 ID
	 * @return
	 */
	// USER_ROLE -> OWNER
	@PreAuthorize("hasAnyRole('Master', 'MANAGER', 'OWNER')")
	@DeleteMapping("/foods/{foodId}")
	public ResponseEntity<ApiResponse<Void>> deleteFood(@PathVariable UUID foodId) {

		foodsService.deleteFood(foodId);

		return ResponseEntity.ok(ApiResponse.ok("메뉴를 성공적으로 삭제했습니다.", null));
	}

	/**
	 * 메뉴 옵션 생성
	 *
	 * @param foodId : 옵션을 추가할 메뉴 ID
	 * @param requestDto : 옵션 정보
	 * @return
	 */
	@PostMapping("/foods/{foodId}/options")
	public ResponseEntity<ApiResponse<List<FoodOptionResponseDto>>> createFoodOption(
		@PathVariable UUID foodId, @RequestBody List<FoodOptionRequestDto> requestDto) {

		List<FoodOptionResponseDto> responseDto = foodsService.createFoodOption(foodId, requestDto);

		return ResponseEntity.ok(ApiResponse.ok("메뉴 옵션을 성공적으로 생성했습니다.", responseDto));
	}

	/**
	 * 메뉴 옵션 조회
	 *
	 * @param foodId : 옵션을 조회할 메뉴 ID
	 * @return : 메뉴의 옵션 List
	 */
	@GetMapping("foods/{foodId}/options")
	public ResponseEntity<ApiResponse<List<FoodOptionResponseDto>>> getFoodOption(@PathVariable UUID foodId) {

		List<FoodOptionResponseDto> responseDto = foodsService.getFoodOption(foodId);

		return ResponseEntity.ok(ApiResponse.ok("메뉴 옵션을 성공적으로 조회했습니다.", responseDto));
	}
}

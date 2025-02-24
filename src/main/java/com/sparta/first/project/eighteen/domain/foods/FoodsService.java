package com.sparta.first.project.eighteen.domain.foods;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.first.project.eighteen.domain.foods.dtos.FoodCreateRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodOptionRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodOptionResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodSearchRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodUpdateRequestDto;
import com.sparta.first.project.eighteen.domain.stores.StoreRepository;
import com.sparta.first.project.eighteen.model.foods.FoodOptions;
import com.sparta.first.project.eighteen.model.foods.Foods;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.utils.GeminiApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodsService {

	private final FoodsRepository foodsRepository;
	private final FoodOptionsRepository foodOptionsRepository;
	private final StoreRepository storesRepository;
	private final GeminiApiClient geminiApiClient;

	/**
	 * 메뉴 생성
	 *
	 * @param requestDto : 메뉴 정보
	 * @return
	 */
	@Transactional
	public FoodResponseDto createFood(FoodCreateRequestDto requestDto) {

		Stores store = storesRepository.findById(requestDto.getStoreId())
			.orElseThrow(() -> new RuntimeException("해당 가게를 찾을 수 없습니다."));

		String foodDesc = (requestDto.getFoodDesc() == null || requestDto.getFoodDesc().isEmpty())
			? geminiApiClient.GeminiResponse(requestDto.getFoodName())
			: requestDto.getFoodDesc();

		Foods food = foodsRepository.save(requestDto.toEntity(store, foodDesc));

		return FoodResponseDto.fromEntity(food);
	}

	/**
	 * 메뉴
	 *
	 * @param storeId : 메뉴가 있는 가게 ID
	 * @param requestDto : 검색 조건
	 * @return : 메뉴에 있는 메뉴 정보
	 */
	@Transactional(readOnly = true)
	public PagedModel<FoodResponseDto> searchFood(UUID storeId, FoodSearchRequestDto requestDto) {

		Stores store = storesRepository.findById(storeId)
			.orElseThrow(() -> new RuntimeException("해당 가게를 찾을 수 없습니다."));

		Pageable pageable = requestDto.toPageable();
		Page<FoodResponseDto> foodPage = foodsRepository.findAllBySearchParam(requestDto, pageable);

		return new PagedModel<>(foodPage);
	}

	/**
	 * 특정 메뉴 조회
	 *
	 * @param foodId : 조회할 메뉴 ID
	 * @return : 조회할 메뉴 정보
	 */
	@Transactional(readOnly = true)
	public FoodResponseDto getFood(UUID foodId) {

		Foods food = foodsRepository.findById(foodId)
			.orElseThrow(() -> new RuntimeException("해당 음식을 찾을 수 없습니다."));

		return FoodResponseDto.fromEntity(food);
	}

	/**
	 * 메뉴 수정
	 *
	 * @param foodId : 수정할 메뉴 ID
	 * @param requestDto : 수정할 메뉴 정보
	 * @return
	 */
	@Transactional
	public FoodResponseDto updateFood(UUID foodId, FoodUpdateRequestDto requestDto) {

		Foods food = foodsRepository.findById(foodId)
			.orElseThrow(() -> new IllegalArgumentException("해당 음식은 존재하지 않습니다."));

		String foodDesc = (requestDto.getFoodDesc() == null || requestDto.getFoodDesc().isEmpty())
			? geminiApiClient.GeminiResponse(requestDto.getFoodName())
			: requestDto.getFoodDesc();

		food.updateFood(requestDto, foodDesc);

		return FoodResponseDto.fromEntity(food);
	}

	/**
	 * 메뉴 삭제
	 *
	 * @param foodId : 삭제할 메뉴 ID
	 */
	@Transactional
	public void deleteFood(UUID foodId) {

		Foods food = foodsRepository.findById(foodId).orElseThrow(() -> new RuntimeException("해당 음식을 찾을 수 없습니다."));

		food.delete(true, "food-test");
	}

	/**
	 * 메뉴 옵션 생성
	 *
	 * @param foodId : 옵션을 추가할 메뉴 ID
	 * @param requestDto : 옵션 정보
	 * @return
	 */
	public List<FoodOptionResponseDto> createFoodOption(UUID foodId, List<FoodOptionRequestDto> requestDto) {

		Foods food = foodsRepository.findById(foodId)
			.orElseThrow(() -> new IllegalArgumentException("해당 음식을 찾을 수 없습니다."));

		List<FoodOptions> foodOptions = requestDto.stream()
			.map(optionDto -> optionDto.toEntity(food))
			.toList();

		foodOptionsRepository.saveAll(foodOptions);

		return FoodOptionResponseDto.fromEntityList(foodOptions);
	}

	/**
	 * 메뉴 옵션 조회
	 *
	 * @param foodId : 옵션을 조회할 메뉴 ID
	 * @return : 메뉴의 옵션 List
	 */
	public List<FoodOptionResponseDto> getFoodOption(UUID foodId) {

		Foods food = foodsRepository.findById(foodId)
			.orElseThrow(() -> new IllegalArgumentException("해당 음식을 찾을 수 없습니다."));

		List<FoodOptions> foodOptions = foodOptionsRepository.findByFoodAndIsDeletedFalse(food);

		return FoodOptionResponseDto.fromEntityList(foodOptions);
	}

	/**
	 * 메뉴 옵션 수정
	 *
	 * @param foodId : 옵션이 존재하는 메뉴 ID
	 * @param optionId : 수정할 옵션 ID
	 * @param requestDto : 수정할 옵션 정보
	 * @return
	 */
	@Transactional
	public FoodOptionResponseDto updateFoodOption(UUID foodId, UUID optionId, FoodOptionRequestDto requestDto) {

		Foods food = foodsRepository.findById(foodId)
			.orElseThrow(() -> new IllegalArgumentException("해당 음식을 찾을 수 없습니다."));

		FoodOptions option = foodOptionsRepository.findById(optionId)
			.orElseThrow(() -> new IllegalArgumentException("해당 음식 옵션을 찾을 수 없습니다."));

		option.updateFoodOption(requestDto);

		return FoodOptionResponseDto.fromEntity(option);
	}

	/**
	 * 메뉴 옵션 삭제
	 *
	 * @param foodId : 옵션이 존재하는 메뉴 ID
	 * @param optionId : 삭제할 옵션 ID
	 * @return
	 */
	@Transactional
	public void deleteFoodOption(UUID foodId, UUID optionId) {

		Foods food = foodsRepository.findById(foodId)
			.orElseThrow(() -> new IllegalArgumentException("해당 음식을 찾을 수 없습니다."));

		FoodOptions option = foodOptionsRepository.findById(optionId)
			.orElseThrow(() -> new IllegalArgumentException("해당 음식 옵션을 찾을 수 없습니다."));

		if (option.getIsDeleted()) {
			throw new IllegalStateException("이미 삭제된 옵션입니다.");
		}

		option.delete(true, "food-test");
	}
}

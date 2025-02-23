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

	@Transactional(readOnly = true)
	public PagedModel<FoodResponseDto> searchFood(UUID storeId, FoodSearchRequestDto requestDto) {

		Stores store = storesRepository.findById(storeId)
			.orElseThrow(() -> new RuntimeException("해당 가게를 찾을 수 없습니다."));

		Pageable pageable = requestDto.toPageable();
		Page<FoodResponseDto> foodPage = foodsRepository.findAllBySearchParam(requestDto, pageable);

		return new PagedModel<>(foodPage);
	}

	@Transactional(readOnly = true)
	public FoodResponseDto getFood(UUID foodId) {

		Foods food = foodsRepository.findById(foodId)
			.orElseThrow(() -> new RuntimeException("해당 음식을 찾을 수 없습니다."));

		return FoodResponseDto.fromEntity(food);
	}

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

	@Transactional
	public void deleteFood(UUID foodId) {

		Foods food = foodsRepository.findById(foodId).orElseThrow(() -> new RuntimeException("해당 음식을 찾을 수 없습니다."));

		food.delete(true, "food-test");
	}

	public List<FoodOptionResponseDto> createFoodOption(UUID foodId, List<FoodOptionRequestDto> requestDto) {

		Foods food = foodsRepository.findById(foodId)
			.orElseThrow(() -> new IllegalArgumentException("해당 음식을 찾을 수 없습니다."));

		List<FoodOptions> foodOptions = requestDto.stream()
			.map(optionDto -> optionDto.toEntity(food))
			.toList();

		foodOptionsRepository.saveAll(foodOptions);

		return FoodOptionResponseDto.fromEntityList(foodOptions);
	}
}

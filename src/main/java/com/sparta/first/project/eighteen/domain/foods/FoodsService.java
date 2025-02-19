package com.sparta.first.project.eighteen.domain.foods;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sparta.first.project.eighteen.domain.foods.dtos.FoodCreateRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodGetResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodSearchRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodSingleResponseDto;
import com.sparta.first.project.eighteen.domain.stores.StoreRepository;
import com.sparta.first.project.eighteen.model.foods.FoodOptions;
import com.sparta.first.project.eighteen.model.foods.Foods;
import com.sparta.first.project.eighteen.model.stores.Stores;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodsService {

	private final FoodsRepository foodsRepository;
	private final FoodOptionsRepository foodOptionsRepository;
	private final StoreRepository storesRepository;

	public FoodResponseDto createFood(FoodCreateRequestDto requestDto) {

		Stores store = storesRepository.findById(requestDto.getStoreId())
			.orElseThrow(() -> new RuntimeException("해당 가게를 찾을 수 없습니다."));

		Foods food = foodsRepository.save(requestDto.toEntity(store));

		if (requestDto.getOptions() != null && !requestDto.getOptions().isEmpty()) {
			List<FoodOptions> foodOptions = requestDto.getOptions()
				.stream()
				.map(optionDto -> optionDto.toEntity(food))
				.toList();

			foodOptionsRepository.saveAll(foodOptions);
		}

		return FoodResponseDto.fromEntity(food);
	}

	public FoodGetResponseDto searchFood(UUID storeId, FoodSearchRequestDto requestDto) {

		PageRequest pageRequest = PageRequest.of(
			requestDto.getPage() - 1,
			requestDto.getLimit(),
			getSort(requestDto.getSort())
		);

		Page<Foods> foodPage = foodsRepository.searchFoods(
			storeId,
			requestDto.getKeyword(),
			pageRequest
		);

		return FoodGetResponseDto.fromEntity(
			foodPage.getContent(),
			(int)foodPage.getTotalElements(),
			foodPage.getTotalPages(),
			requestDto.getPage()
		);
	}

	public FoodSingleResponseDto getFood(UUID foodId) {

		Foods food = foodsRepository.findById(foodId)
			.orElseThrow(() -> new RuntimeException("해당 음식을 찾을 수 없습니다."));

		return FoodSingleResponseDto.fromEntity(food);
	}

	public void deleteFood(UUID foodId) {

		Foods food = foodsRepository.findById(foodId).orElseThrow(() -> new RuntimeException("해당 음식을 찾을 수 없습니다."));

		food.delete(true, "food-test");
	}

	private Sort getSort(String sort) {
		if (sort == null || sort.isEmpty()) {
			return Sort.unsorted();
		}

		return switch (sort) {
			case "price_asc" -> Sort.by(Sort.Direction.ASC, "foodPrice");
			case "price_desc" -> Sort.by(Sort.Direction.DESC, "foodPrice");
			case "review_desc" -> Sort.by(Sort.Direction.DESC, "foodReviewCount");
			case "order_desc" -> Sort.by(Sort.Direction.DESC, "foodOrderCount");
			default -> Sort.unsorted();
		};
	}
}

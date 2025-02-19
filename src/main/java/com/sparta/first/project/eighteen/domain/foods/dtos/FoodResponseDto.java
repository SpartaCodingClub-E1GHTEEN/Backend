package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.sparta.first.project.eighteen.model.foods.FoodStatus;
import com.sparta.first.project.eighteen.model.foods.Foods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponseDto {
	private UUID id;
	private UUID storeId;
	private String foodName;
	private String foodDesc;
	private int foodPrice;
	private String foodImageUrl;
	private FoodStatus foodStatus;
	private boolean isRecommended;
	private int foodReviewCount;
	private int foodOrderCount;
	private List<FoodOptionResponseDto> options;

	public static FoodResponseDto fromEntity(Foods food) {
		return new FoodResponseDto(
			food.getId(),
			food.getStore().getId(),
			food.getFoodName(),
			food.getFoodDesc(),
			food.getFoodPrice(),
			food.getFoodImageUrl(),
			food.getFoodStatus(),
			food.isRecommended(),
			food.getFoodReviewCount(),
			food.getFoodOrderCount(),
			food.getFoodOptions().stream()
				.map(FoodOptionResponseDto::fromEntity)
				.collect(Collectors.toList())
		);
	}
}

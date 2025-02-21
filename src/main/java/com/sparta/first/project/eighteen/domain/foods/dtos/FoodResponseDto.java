package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sparta.first.project.eighteen.model.foods.FoodStatus;
import com.sparta.first.project.eighteen.model.foods.Foods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FoodResponseDto {
	private UUID id;
	private UUID storeId;
	private String foodName;
	private String foodDesc;
	private int foodPrice;
	private String foodImageUrl;
	private FoodStatus foodStatus;
	private Boolean isRecommended;
	private int foodOrderCount;
	private List<FoodOptionResponseDto> foodOptions;

	public static FoodResponseDto fromEntity(Foods food) {

		List<FoodOptionResponseDto> optionsDto = new ArrayList<>();

		if (food.getFoodOptions() != null) {
			optionsDto = food.getFoodOptions().stream()
				.map(FoodOptionResponseDto::fromEntity)
				.toList();
		}

		return FoodResponseDto.builder()
			.id(food.getId())
			.storeId(food.getStore().getId())
			.foodName(food.getFoodName())
			.foodDesc(food.getFoodDesc())
			.foodPrice(food.getFoodPrice())
			.foodImageUrl(food.getFoodImageUrl())
			.isRecommended(food.isRecommended())
			.foodOrderCount(food.getFoodOrderCount())
			.foodOptions(optionsDto)
			.build();
	}
}

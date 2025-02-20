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
public class FoodItemDto {
	private UUID id;
	private String foodName;
	private String foodDesc;
	private int foodPrice;
	private String foodImageUrl;
	private FoodStatus foodStatus;
	private Boolean isRecommended;
	private Integer foodOrderCount;
	private List<FoodOptionResponseDto> options;

	public static FoodItemDto fromEntity(Foods food) {

		return new FoodItemDto(
			food.getId(),
			food.getFoodName(),
			food.getFoodDesc(),
			food.getFoodPrice(),
			food.getFoodImageUrl(),
			food.getFoodStatus(),
			food.isRecommended(),
			food.getFoodOrderCount(),
			food.getFoodOptions().stream()
				.map(FoodOptionResponseDto::fromEntity)
				.collect(Collectors.toList())
		);
	}
}

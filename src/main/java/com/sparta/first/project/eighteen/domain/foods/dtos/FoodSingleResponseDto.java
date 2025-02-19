package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.UUID;

import com.sparta.first.project.eighteen.model.foods.Foods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodSingleResponseDto {

	private UUID storeId;
	private FoodItemDto food;

	public static FoodSingleResponseDto fromEntity(Foods food) {

		return new FoodSingleResponseDto(
			food.getStore().getId(),
			FoodItemDto.fromEntity(food)
		);
	}
}

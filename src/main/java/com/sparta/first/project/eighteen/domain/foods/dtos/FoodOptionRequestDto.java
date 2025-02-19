package com.sparta.first.project.eighteen.domain.foods.dtos;

import com.sparta.first.project.eighteen.model.foods.FoodOptions;
import com.sparta.first.project.eighteen.model.foods.Foods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodOptionRequestDto {
	private String optionName;
	private int optionPrice;

	public FoodOptions toEntity(Foods food) {

		return FoodOptions.builder()
			.optionName(optionName)
			.optionPrice(optionPrice)
			.food(food)
			.build();
	}
}

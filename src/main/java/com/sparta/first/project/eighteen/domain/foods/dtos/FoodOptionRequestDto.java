package com.sparta.first.project.eighteen.domain.foods.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodOptionRequestDto {
	private String optionName;
	private int optionPrice;
}

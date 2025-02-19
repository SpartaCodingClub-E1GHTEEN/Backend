package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.UUID;

import com.sparta.first.project.eighteen.model.foods.FoodOptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodOptionResponseDto {

	private UUID id;
	private String optionName;
	private int optionPrice;

	public static FoodOptionResponseDto fromEntity(FoodOptions option) {
		return new FoodOptionResponseDto(
			option.getId(),
			option.getOptionName(),
			option.getOptionPrice()
		);
	}
}

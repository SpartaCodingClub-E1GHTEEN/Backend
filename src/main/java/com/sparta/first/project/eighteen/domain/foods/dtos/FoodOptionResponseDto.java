package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.List;
import java.util.UUID;

import com.sparta.first.project.eighteen.model.foods.FoodOptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodOptionResponseDto {

	private UUID id;
	private String optionName;
	private int optionPrice;

	public static FoodOptionResponseDto fromEntity(FoodOptions option) {
		return FoodOptionResponseDto.builder()
			.id(option.getId())
			.optionName(option.getOptionName())
			.optionPrice(option.getOptionPrice())
			.build();
	}

	public static List<FoodOptionResponseDto> fromEntityList(List<FoodOptions> option) {
		return option.stream()
			.map(FoodOptionResponseDto::fromEntity)
			.toList();
	}
}

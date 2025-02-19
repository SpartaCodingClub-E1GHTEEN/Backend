package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.sparta.first.project.eighteen.model.foods.Foods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodGetResponseDto {
	private UUID storeId;
	private List<FoodItemDto> foods;
	private int totalFoods;
	private int totalPages;
	private int currentPage;

	public static FoodGetResponseDto fromEntity(List<Foods> foods, int totalFoods, int totalPages, int currentPage) {

		return new FoodGetResponseDto(
			foods.isEmpty() ? null :
				foods.get(0).getStore().getId(),
			foods.stream().map(FoodItemDto::fromEntity)
				.collect(Collectors.toList()),
			totalFoods,
			totalPages,
			currentPage
		);
	}
}

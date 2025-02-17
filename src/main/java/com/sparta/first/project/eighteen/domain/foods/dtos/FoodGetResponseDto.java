package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodGetResponseDto {
	private String storeId;
	private List<FoodItemDto> foods;
	private int totalFoods;
	private int totalPages;
	private int currentPage;

}

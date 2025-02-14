package com.sparta.first.project.eighteen.domain.foods.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodSearchRequestDto {
	private String keyword;
	private String filter;
	private String sort;
	private int page = 1;
	private int limit = 10;
}

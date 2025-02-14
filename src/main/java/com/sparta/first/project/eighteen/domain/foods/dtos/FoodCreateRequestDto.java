package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodCreateRequestDto {
	private String storeId;
	private String foodName;
	private String foodDesc;
	private int foodPrice;
	private String foodCategory;
	private String foodImageUrl;
	private boolean isRecommended;
	private List<FoodOptionRequestDto> options;
}

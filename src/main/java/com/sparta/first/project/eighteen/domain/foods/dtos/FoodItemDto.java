package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemDto {
	private String id;
	private String foodName;
	private String foodDesc;
	private Integer foodPrice;
	private String foodCategory;
	private String foodImageUrl;
	private String foodStatus;
	private Boolean isRecommended;
	private Integer foodReviewCount;
	private Integer foodOrderCount;
	private List<FoodOptionResponseDto> options;

}

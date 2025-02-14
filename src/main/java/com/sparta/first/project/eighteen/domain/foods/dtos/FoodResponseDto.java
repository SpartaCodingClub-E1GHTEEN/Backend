package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponseDto {
	private String id;
	private String storeId;
	private String foodName;
	private String foodDesc;
	private int foodPrice;
	private String foodCategory;
	private String foodImageUrl;
	private String foodStatus;
	private boolean isRecommended;
	private int foodReviewCount;
	private int foodOrderCount;
	private LocalDateTime createdAt;
	private List<FoodOptionResponseDto> options;
}

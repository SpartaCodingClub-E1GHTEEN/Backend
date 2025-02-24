package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.UUID;

import com.sparta.first.project.eighteen.model.foods.FoodStatus;
import com.sparta.first.project.eighteen.model.foods.Foods;
import com.sparta.first.project.eighteen.model.stores.Stores;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FoodCreateRequestDto {

	@NotNull
	private UUID storeId;

	@NotBlank
	private String foodName;

	private String foodDesc = "";

	@Min(0)
	private int foodPrice;

	private String foodImageUrl;

	@NotNull
	private FoodStatus foodStatus;

	private Boolean isRecommended;

	public Foods toEntity(Stores store, String generatedFoodDesc) {

		return Foods.builder()
			.foodName(foodName)
			.foodDesc(generatedFoodDesc)
			.foodPrice(foodPrice)
			.foodImageUrl(foodImageUrl)
			.foodStatus(foodStatus)
			.isRecommended(isRecommended)
			.store(store)
			.build();
	}
}

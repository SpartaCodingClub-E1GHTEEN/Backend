package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.List;
import java.util.UUID;

import com.sparta.first.project.eighteen.model.foods.FoodStatus;
import com.sparta.first.project.eighteen.model.foods.Foods;
import com.sparta.first.project.eighteen.model.stores.Stores;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodCreateRequestDto {

	@NotNull
	private UUID storeId;

	@NotBlank
	private String foodName;

	private String foodDesc;

	@Min(0)
	private int foodPrice;

	private String foodImageUrl;

	@NotNull
	private FoodStatus foodStatus;

	private boolean isRecommended = false;

	private List<FoodOptionRequestDto> options;

	public Foods toEntity(Stores store) {
		return Foods.builder()
			.foodName(foodName)
			.foodDesc(foodDesc)
			.foodPrice(foodPrice)
			.foodImageUrl(foodImageUrl)
			.foodStatus(foodStatus)
			.isRecommended(isRecommended)
			.store(store)
			.build();
	}
}

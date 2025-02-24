package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.List;

import com.sparta.first.project.eighteen.model.foods.FoodStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class FoodUpdateRequestDto {

	@NotBlank(message = "메뉴 이름은 필수입니다.")
	private String foodName;

	private String foodDesc;

	@NotBlank(message = "메뉴 가격은 필수입니다.")
	@Min(value = 0, message = "메뉴 가격은 0원 이상이어야 합니다.")
	private int foodPrice;

	private String foodImageUrl;

	@NotBlank(message = "메뉴 판매 상태는 필수입니다.")
	private FoodStatus foodStatus;

	private Boolean isRecommended = false;

	private List<FoodOptionRequestDto> foodOptions;
}

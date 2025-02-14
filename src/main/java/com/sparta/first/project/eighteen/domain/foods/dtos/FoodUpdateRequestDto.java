package com.sparta.first.project.eighteen.domain.foods.dtos;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodUpdateRequestDto {

	@NotBlank(message = "메뉴 이름은 필수입니다.")
	private String foodName;

	private String foodDesc;

	@NotBlank(message = "메뉴 가격은 필수입니다.")
	@Min(value = 0, message = "메뉴 가격은 0원 이상이어야 합니다.")
	private int foodPrice;

	@NotBlank(message = "카테고리는 필수입니다.")
	private String foodCategory;

	private String foodImageUrl;

	@NotBlank(message = "메뉴 판매 상태는 필수입니다.")
	private String foodStatus;

	private boolean isRecommended = false;

	private List<FoodOptionRequestDto> options;
}

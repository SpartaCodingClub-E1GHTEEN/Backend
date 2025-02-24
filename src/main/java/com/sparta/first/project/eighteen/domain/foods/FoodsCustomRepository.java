package com.sparta.first.project.eighteen.domain.foods;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.first.project.eighteen.domain.foods.dtos.FoodResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodSearchRequestDto;

public interface FoodsCustomRepository {
	Page<FoodResponseDto> findAllBySearchParam(FoodSearchRequestDto requestDto, Pageable pageable);
}

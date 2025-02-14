package com.sparta.first.project.eighteen.domain.foods;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.domain.foods.dtos.FoodCreateRequestDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodCreateResponseDto;

@RestController
@RequestMapping("/api/v1")
public class FoodsController {
	private FoodsService foodsService;

	@PostMapping("/food")
	public ResponseEntity<FoodCreateResponseDto> createFood(@RequestBody FoodCreateRequestDto requestDto) {

		return null;
	}
}

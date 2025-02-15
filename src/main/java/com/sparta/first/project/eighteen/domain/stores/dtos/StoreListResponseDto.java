package com.sparta.first.project.eighteen.domain.stores.dtos;

import java.util.ArrayList;
import java.util.List;

public class StoreListResponseDto {

	// 식당 ID
	private String id;

	// 식당명
	private String storeName;

	// 식당 지역
	private String storeRegion;

	// 식당 평점
	private double storeRating;

	// 식당에 판매하는 음식 리스트 -> FoodResponseDto 리스트로 변환
	List<?> foodList;

	// 테스트를 위한 임시 생성자
	public StoreListResponseDto (StoreRequestDto storeRequestDto) {
		this.id = "1";
		this.storeName = "한식당";
		this.storeRegion = "서울 광화문";
		this.storeRating = 4.3;
		// FoodResponseDto 리스트로 변환
		this.foodList = new ArrayList<>();
	}

}

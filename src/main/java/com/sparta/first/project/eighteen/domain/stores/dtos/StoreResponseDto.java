package com.sparta.first.project.eighteen.domain.stores.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreResponseDto {

	// 식당 ID
	private String id;

	// 식당 주인
	private String userName;

	// 식당명
	private String storeName;

	// 식당 소개
	private String storeDesc;

	// 식당 지역
	private String storeRegion;

	// 식당 카테고리
	private String storeCategory;

	// 식당 평점
	private double storeRating;

	// 식당 리뷰 개수
	private int storeReviewCnt;
	
	// 식당에 판매하는 음식 리스트 -> FoodResponseDto 리스트로 변환
	List<?> foodList; 

	// 테스트를 위한 임시 생성자
	public StoreResponseDto(StoreRequestDto storeRequestDto) {
		this.id = "1";
		this.userName = "김사장";
		this.storeName = storeRequestDto.getStoreName();
		this.storeDesc = storeRequestDto.getStoreDesc();
		this.storeRegion = storeRequestDto.getStoreRegion();
		this.storeCategory = storeRequestDto.getStoreCategory();
		this.storeRating = 4.3;
		this.storeReviewCnt = 100;
		// FoodResponseDto 리스트로 변환
		this.foodList = new ArrayList<>();
	}

}

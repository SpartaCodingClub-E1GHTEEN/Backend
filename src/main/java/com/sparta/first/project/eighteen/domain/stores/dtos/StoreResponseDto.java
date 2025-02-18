package com.sparta.first.project.eighteen.domain.stores.dtos;

import java.util.UUID;

import com.sparta.first.project.eighteen.model.stores.StoreCategory;
import com.sparta.first.project.eighteen.model.stores.Stores;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	private StoreCategory storeCategory;

	// 식당 평점
	private double storeRating;

	// 식당 리뷰 개수
	private int storeReviewCnt;

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
	}

	public StoreResponseDto fromEntity(Stores stores) {
		return StoreResponseDto.builder()
			.id(stores.getId().toString())
			.userName(stores.getUserId().getUsername())
			.storeName(stores.getStoreName())
			.storeDesc(stores.getStoreDesc())
			.storeRegion(stores.getStoreRegion())
			.storeCategory(stores.getStoreCategory())
			.storeRating(stores.getStoreRating())
			.build();
	}

}

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
	private long storeReviewCnt;

	// 식당 이미지
	private String storeImgUrl;

	// 식당 배달 팁
	private int storeDeliveryPrice;

	// 식당 생성일자
	private String createdAt;

	public static StoreResponseDto fromEntity(Stores stores) {
		return StoreResponseDto.builder()
			.id(stores.getId().toString())
			.userName(stores.getUserId().getUsername())
			.storeName(stores.getStoreName())
			.storeDesc(stores.getStoreDesc())
			.storeRegion(stores.getStoreRegion())
			.storeCategory(stores.getStoreCategory())
			.storeImgUrl(stores.getStoreImgUrl())
			.storeDeliveryPrice(stores.getStoreDeliveryPrice())
			.createdAt(stores.getCreatedAt().toString())
			.build();
	}

	public static StoreResponseDto fromEntityReview(Stores stores, long storeReviewCnt, double storeRating) {
		return StoreResponseDto.builder()
			.id(stores.getId().toString())
			.userName(stores.getUserId().getUsername())
			.storeName(stores.getStoreName())
			.storeDesc(stores.getStoreDesc())
			.storeRegion(stores.getStoreRegion())
			.storeCategory(stores.getStoreCategory())
			.storeRating(stores.getStoreRating())
			.storeDeliveryPrice(stores.getStoreDeliveryPrice())
			.storeImgUrl(stores.getStoreImgUrl())
			.storeReviewCnt(storeReviewCnt)
			.storeRating(storeRating)
			.createdAt(stores.getCreatedAt().toString())
			.build();
	}

}

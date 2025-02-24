package com.sparta.first.project.eighteen.domain.stores.dtos;

import java.util.ArrayList;
import java.util.List;

import com.sparta.first.project.eighteen.model.stores.Stores;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreListResponseDto {

	// 식당 ID
	private String id;

	// 식당명
	private String storeName;

	// 식당 지역
	private String storeRegion;

	// 식당 평점
	private double storeRating;

	// 식당 이미지
	private String storeImgUrl;

	// 식당 배달팁
	private int storeDeliveryPrice;

	public static StoreListResponseDto fromEntity(Stores store) {
		return StoreListResponseDto.builder()
			.id(store.getId().toString())
			.storeName(store.getStoreName())
			.storeImgUrl(store.getStoreImgUrl())
			.storeRegion(store.getStoreRegion())
			.storeRating(store.getStoreRating())
			.storeDeliveryPrice(store.getStoreDeliveryPrice())
			.build();
	}

}

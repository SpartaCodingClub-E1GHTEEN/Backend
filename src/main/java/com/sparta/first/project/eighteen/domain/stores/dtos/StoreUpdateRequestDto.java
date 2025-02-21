package com.sparta.first.project.eighteen.domain.stores.dtos;

import com.sparta.first.project.eighteen.model.stores.StoreCategory;
import com.sparta.first.project.eighteen.model.stores.Stores;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreUpdateRequestDto {

	// 식당명
	private String storeName;

	// 식당 소개
	private String storeDesc;

	// 식당 지역
	private String storeRegion;

	// 식당 카테고리
	private StoreCategory storeCategory;

	// 식당 배달팁
	private int storeDeliveryPrice;

	// 식당 이미지
	private String storeImgUrl;

	public Stores toEntity() {
		return Stores.builder()
			.storeName(this.storeName)
			.storeDesc(this.storeDesc)
			.storeRegion(this.storeRegion)
			.storeImgUrl(this.storeImgUrl)
			.storeDeliveryPrice(this.storeDeliveryPrice)
			.storeCategory(this.storeCategory)
			.build();
	}
}

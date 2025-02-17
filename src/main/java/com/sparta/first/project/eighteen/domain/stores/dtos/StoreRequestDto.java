package com.sparta.first.project.eighteen.domain.stores.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreRequestDto {

	// 식당명
	private String storeName;

	// 식당 소개
	private String storeDesc;

	// 식당 지역
	private String storeRegion;

	// 식당 카테고리
	private String storeCategory;

}

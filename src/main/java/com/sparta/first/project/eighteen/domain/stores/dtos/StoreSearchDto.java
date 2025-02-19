package com.sparta.first.project.eighteen.domain.stores.dtos;

import org.springframework.data.domain.Pageable;

import com.sparta.first.project.eighteen.model.stores.StoreCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreSearchDto {

	// 식당명
	private String storeName;

	// 식당 지역
	private String storeRegion;

	// 식당 카테고리
	private StoreCategory storeCategory;

	// 식당 최소 배달팁
	private int minDeliveryPrice;

	// 식당 최대 배달팁
	private int maxDeliveryPrice;

}
package com.sparta.first.project.eighteen.domain.stores.dtos;

import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreSearchDto {

	// 식당명
	private String storeName;

	// 식당 지역
	private String storeRegion;

	// 식당 카테고리
	private String storeCategory;

	// pageable
	private Pageable pageable;

}
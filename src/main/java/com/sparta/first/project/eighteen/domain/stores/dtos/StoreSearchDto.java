package com.sparta.first.project.eighteen.domain.stores.dtos;

import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
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

	// 가져올 페이지 수
	@JsonSetter(nulls = Nulls.SKIP)
	private int page = 0;

	// 페이지에 가져올 데이터 크기
	@JsonSetter(nulls = Nulls.SKIP)
	private int size = 10;

	// 가져올 정렬 기준
	@JsonSetter(nulls = Nulls.SKIP)
	private String sortBy;

	@JsonSetter(nulls = Nulls.SKIP)
	private Sort.Direction direction = Sort.Direction.ASC;

}
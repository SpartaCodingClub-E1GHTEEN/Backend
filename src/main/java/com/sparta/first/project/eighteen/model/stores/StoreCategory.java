package com.sparta.first.project.eighteen.model.stores;

public enum StoreCategory {

	KOREAN_FOOD("한식"),
	WESTERN_FOOD("양식"),
	JAPANESE_FOOD("일식"),
	CHINESE_FOOD("중식"),
	DESSERT("디저트"),
	FAST_FOOD("패스트푸드"),
	SNACK_FOOD("분식");

	private final String category;

	StoreCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}
}
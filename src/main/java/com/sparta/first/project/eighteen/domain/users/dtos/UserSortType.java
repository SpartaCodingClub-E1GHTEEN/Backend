package com.sparta.first.project.eighteen.domain.users.dtos;

import org.springframework.data.domain.Sort;

public enum UserSortType {
	SIGN_UP_DATE_ASC(Sort.Direction.ASC, Field.CREATED_AT),
	SIGN_UP_DATE_DESC(Sort.Direction.DESC, Field.CREATED_AT);
	private final Sort.Direction direction;
	private final String properties;

	UserSortType(Sort.Direction direction, String properties) {
		this.direction = direction;
		this.properties = properties;
	}

	public String getProperties() {
		return properties;
	}

	public Sort.Direction getDirection() {
		return direction;
	}

	public Sort toSort() {
		return Sort.by(this.getDirection(), this.getProperties());
	}

	public static class Field { // 등급이나 주문 횟수 등 추가 가능
		public static final String CREATED_AT = "createdAt";
	}
}

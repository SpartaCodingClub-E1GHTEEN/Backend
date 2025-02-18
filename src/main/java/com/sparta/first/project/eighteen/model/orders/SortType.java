package com.sparta.first.project.eighteen.model.orders;

import org.springframework.data.domain.Sort;

import lombok.Getter;

@Getter
public enum SortType {
	DATE_DESC(Sort.Direction.DESC, "orderTime"),
	DATE_ASC(Sort.Direction.ASC, "orderTime");

	private final Sort.Direction sortDirection;
	private final String properties;

	SortType(Sort.Direction sortDirection, String properties) {
		this.sortDirection = sortDirection;
		this.properties = properties;
	}
}

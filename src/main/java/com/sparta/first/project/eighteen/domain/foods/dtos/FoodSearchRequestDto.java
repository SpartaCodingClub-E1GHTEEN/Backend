package com.sparta.first.project.eighteen.domain.foods.dtos;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodSearchRequestDto {

	private String keyword = "";

	private int page = 0;

	private int size = 10;

	private Sort.Direction direction = Sort.Direction.ASC;

	private String sortBy = "createdAt";

	public Pageable toPageable() {
		return PageRequest.of(page, size, Sort.by(direction, sortBy));
	}
}

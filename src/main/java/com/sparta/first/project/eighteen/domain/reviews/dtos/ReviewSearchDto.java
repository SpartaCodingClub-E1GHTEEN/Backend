package com.sparta.first.project.eighteen.domain.reviews.dtos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

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
public class ReviewSearchDto {

	// 검색할 리뷰 내용
	private String reviewContent;

	// 검색할 리뷰 평점들
	private List<Integer> reviewRatings;

	// 페이지 수
	@JsonSetter(nulls = Nulls.SKIP)
	private int page = 0;

	@JsonSetter(nulls = Nulls.SKIP)
	private int size = 10;

	@JsonSetter(nulls = Nulls.SKIP)
	private String sortBy = "createdAt";

	@JsonSetter(nulls = Nulls.SKIP)
	private Sort.Direction direction = Sort.Direction.ASC;
}
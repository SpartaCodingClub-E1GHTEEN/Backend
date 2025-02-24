package com.sparta.first.project.eighteen.domain.reviews.dtos;

import com.sparta.first.project.eighteen.model.reviews.Reviews;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
public class ReviewUpdateRequestDto {

	// 리뷰 내용
	@Size(min = 5, max = 500, message = "최소 5자 이상, 500자 이하의 리뷰를 작성해주세요")
	private String reviewContent;

	// 리뷰 별점 (Validation -> 1~5 자연수)
	@Min(1)
	@Max(5)
	private int reviewRating;

	// 리뷰 이미지
	private String reviewImgUrl;

	public Reviews toEntity() {
		return Reviews.builder()
			.reviewRating(this.reviewRating)
			.reviewContent(this.reviewContent)
			.reviewImgUrl(this.reviewImgUrl)
			.build();
	}

}
package com.sparta.first.project.eighteen.domain.reviews.dtos;

import com.sparta.first.project.eighteen.model.reviews.Reviews;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

	public Reviews updateEntity(Reviews review) {
		return Reviews.builder()
			.id(review.getId())
			.reviewContent(this.reviewContent != null ? this.reviewContent : review.getReviewContent())
			// 애초에 null일 수 없는 값이라 조건문 필요 X (@Min, @Max 사용 중)
			.reviewRating(this.reviewRating)
			.reviewImgUrl(this.reviewImgUrl != null ? this.reviewImgUrl : review.getReviewImgUrl())
			.usersId(review.getUsersId())
			.storeId(review.getStoreId())
			.orderId(review.getOrderId())
			.build();
	}

}
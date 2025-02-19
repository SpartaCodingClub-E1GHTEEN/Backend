package com.sparta.first.project.eighteen.domain.reviews.dtos;

import com.sparta.first.project.eighteen.model.reviews.Reviews;

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
public class ReviewResponseDto {

	// 리뷰 ID
	private String id;

	// 주문 ID
	private String orderId;

	// 리뷰 작성자
	private String reviewNickname;

	// 리뷰 주문 내역 (메뉴 명)
	private String reviewOrders;

	// 리뷰 내용
	private String reviewContent;

	// 리뷰 별점
	private int reviewRating;

	// 리뷰 이미지
	private String reviewImgUrl;

	public static ReviewResponseDto fromEntity(Reviews review) {
		return ReviewResponseDto.builder()
			.id(review.getId().toString())
			.reviewNickname(review.getUsersId().getUsername())
			.orderId(review.getOrderId().toString())
			// .reviewOrders(review.getOrderId().getOrderDetails().toString())
			.reviewContent(review.getReviewContent())
			.reviewRating(review.getReviewRating())
			.reviewImgUrl(review.getReviewImgUrl())
			.build();
	}

}
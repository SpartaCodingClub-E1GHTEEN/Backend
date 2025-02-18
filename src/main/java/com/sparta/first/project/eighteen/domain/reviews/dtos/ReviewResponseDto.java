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

	// 테스트를 위한 임시 생성자
	public ReviewResponseDto(ReviewCreateRequestDto reviewRequestDto) {
		this.id = "1";
		this.reviewNickname = "김손님";
		this.orderId = reviewRequestDto.getOrderId();
		this.reviewOrders = "김밥, 떡볶이, 등등 ..";
		this.reviewContent = reviewRequestDto.getReviewContent();
		this.reviewRating = reviewRequestDto.getReviewRating();
		this.reviewImgUrl = reviewRequestDto.getReviewImgUrl();
	}

	// 테스트를 위한 임시 생성자
	public ReviewResponseDto(ReviewUpdateRequestDto reviewRequestDto) {
		this.id = "1";
		this.reviewNickname = "이손님";
		this.reviewOrders = "김밥, 떡볶이, 순대";
		this.reviewContent = reviewRequestDto.getReviewContent();
		this.reviewRating = reviewRequestDto.getReviewRating();
		this.reviewImgUrl = reviewRequestDto.getReviewImgUrl();
	}

	public ReviewResponseDto fromEntity(Reviews review) {
		return ReviewResponseDto.builder()
			.id(review.getId().toString())
			.reviewNickname(review.getUsersId().getUsername())
			// .reviewOrders(review.getOrderId().getOrderDetails().toString())
			.reviewContent(review.getReviewContent())
			.reviewRating(review.getReviewRating())
			.reviewImgUrl(review.getReviewImgUrl())
			.build();
	}

}

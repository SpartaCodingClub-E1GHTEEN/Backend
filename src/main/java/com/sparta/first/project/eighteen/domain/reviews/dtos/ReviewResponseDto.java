package com.sparta.first.project.eighteen.domain.reviews.dtos;

import java.util.List;

import com.sparta.first.project.eighteen.model.orders.OrderDetails;
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

	// 리뷰 생성일자
	private String createdAt;

	public static ReviewResponseDto fromEntity(Reviews review) {
		return ReviewResponseDto.builder()
			.id(review.getId().toString())
			.reviewNickname(review.getUsersId().getUsername())
			.orderId(review.getOrderId().getId().toString())
			.reviewOrders(getOrderFoodName(review.getOrderDetails()))
			.reviewContent(review.getReviewContent())
			.reviewRating(review.getReviewRating())
			.reviewImgUrl(review.getReviewImgUrl() == null ? "-" : review.getReviewImgUrl())
			.createdAt(review.getCreatedAt().toString())
			.build();
	}

	public static String getOrderFoodName(List<OrderDetails> orderDetails) {
		StringBuilder sb = new StringBuilder();

		if (orderDetails.size() == 1) {
			return orderDetails.get(0).getFoodName();
		}

		for (int i=0; i<orderDetails.size(); i++) {
			sb.append(orderDetails.get(i).getFoodName()).append(", ");
		}

		return (sb.length() < 15) ? sb.toString() : sb.toString().substring(15) + "...";
	}

}
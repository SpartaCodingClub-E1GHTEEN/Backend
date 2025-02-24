package com.sparta.first.project.eighteen.domain.reviews.dtos;

import java.util.List;
import java.util.UUID;

import com.sparta.first.project.eighteen.model.orders.OrderDetails;
import com.sparta.first.project.eighteen.model.orders.Orders;
import com.sparta.first.project.eighteen.model.reviews.Reviews;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewCreateRequestDto {

	// 리뷰를 작성할 주문 ID
	private UUID orderId;

	// 리뷰 내용
	@Size(min = 5, max = 500, message = "최소 5자 이상, 500자 이하의 리뷰를 작성해주세요")
	private String reviewContent;

	// 리뷰 별점 (Validation -> 1~5 자연수)
	@Min(1)
	@Max(5)
	private int reviewRating;

	// 리뷰 이미지
	private String reviewImgUrl;

	public Reviews toEntity(Stores store, Orders order, Users user, List<OrderDetails> foodNames) {
		return Reviews.builder()
			.storeId(store)
			.orderId(order)
			.usersId(user)
			.reviewContent(this.reviewContent)
			.reviewRating(this.reviewRating)
			.reviewImgUrl(this.reviewImgUrl)
			.orderDetails(foodNames)
			.build();
	}

}
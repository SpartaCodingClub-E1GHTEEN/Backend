package com.sparta.first.project.eighteen.model.reviews;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;
import com.sparta.first.project.eighteen.model.orders.OrderDetails;
import com.sparta.first.project.eighteen.model.orders.Orders;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_reviews")
public class Reviews extends BaseEntity {

	// 리뷰 ID
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	// 리뷰 내용
	@Column
	private String reviewContent;

	// 리뷰 별점
	@Column
	private int reviewRating;

	// 리뷰 이미지
	@Column
	private String reviewImgUrl;

	// 리뷰에 포함될 음식 명
	@OneToMany
	@JoinColumn(name = "order_detail_id", referencedColumnName = "id")
	private List<OrderDetails> orderDetails = new ArrayList<>();

	// 주문 ID (해당 주문에 대한 리뷰)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Orders orderId;

	// 식당 ID (식당에 대한 리뷰)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", referencedColumnName = "id")
	private Stores storeId;

	// 회원 ID (리뷰 작성자)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users usersId;

	/**
	 * 리뷰 수정 메서드
	 * @param updateReview : 수정할 리뷰 내용
	 */
	public void updateReview(Reviews updateReview) {
		Optional.ofNullable(updateReview.getReviewContent()).ifPresent(reviewContent -> this.reviewContent = reviewContent);
		Optional.ofNullable(updateReview.getReviewRating()).ifPresent(reviewRating -> this.reviewRating = reviewRating);
		Optional.ofNullable(updateReview.getReviewImgUrl()).ifPresent(reviewImgUrl -> this.reviewImgUrl = reviewImgUrl);
	}
}
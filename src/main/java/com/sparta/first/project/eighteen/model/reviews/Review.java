package com.sparta.first.project.eighteen.model.reviews;

import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_reviews")
public class Review extends BaseEntity {

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

	// 주문 ID (해당 주문에 대한 리뷰)
	// 연관 관계
	// @Column(name = "order_id")
	// private Order orderId;

	// 식당 ID (식당에 대한 리뷰)
	// 연관 관계
	// @Column(name = "store_id")
	// private Store storeId;

	// 회원 ID (리뷰 작성자)
	// 연관 관계
	// @Column(name = "user_id")
	// private User userId;

}
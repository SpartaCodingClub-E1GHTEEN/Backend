package com.sparta.first.project.eighteen.model.reviews;

import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;
import com.sparta.first.project.eighteen.model.orders.Orders;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	// 주문 ID (해당 주문에 대한 리뷰)
	@OneToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Orders orderId;

	// 식당 ID (식당에 대한 리뷰)
	@ManyToOne
	@JoinColumn(name = "store_id", referencedColumnName = "id")
	private Stores storeId;

	// 회원 ID (리뷰 작성자)
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users usersId;

}
package com.sparta.first.project.eighteen.model.stores;

import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "p_stores")
public class Store extends BaseEntity {

	// 가게 ID
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	// 식당명
	@Column
	private String storeName;

	// 식당 소개(설명)
	@Column
	private String storeDesc;

	// 식당 지역
	@Column
	private String storeRegion;

	// 식당 카테고리
	@Column
	@Enumerated(EnumType.STRING)
	private StoreCategory storeCategory;

	// 식당 평점 -> 계산을 미리 할지, 계산해서 넣어둘 지
	@Column
	private double storeRating;

	// 회원 ID (식당 주인)
	// 연관 관계
	// @Column(name = "user_id")
	// private User userId;

}

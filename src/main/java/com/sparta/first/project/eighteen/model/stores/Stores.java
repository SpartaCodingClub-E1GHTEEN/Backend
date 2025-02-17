package com.sparta.first.project.eighteen.model.stores;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;
import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "p_stores")
public class Stores extends BaseEntity {

	// 식당 ID
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

	// 식당 이미지
	@Column
	private String storeImgUrl;

	// 식당 배달팁
	@Column
	private int storeDeliveryPrice;

	// 식당 카테고리
	@Column
	@Enumerated(EnumType.STRING)
	private StoreCategory storeCategory;

	// 식당 평점 -> 계산을 미리 할지, 계산해서 넣어둘 지
	@Column
	private double storeRating;

	// 회원 ID (식당 주인)
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users userId;

	public void delete(boolean flag, String userId) {
		super.isDeleted = flag;
		super.deletedAt = LocalDateTime.now();
		super.deletedBy = userId;
	}

}

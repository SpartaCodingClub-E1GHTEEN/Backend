package com.sparta.first.project.eighteen.model.foods;

import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_foods")
public class Foods extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column
	private UUID storeId;

	@Column
	private String foodName;

	@Column
	private String foodDesc;

	@Column
	private int foodPrice;

	@Column
	private String foodCategory;

	@Column
	private String foodImageUrl;

	@Column
	private String foodStatus;

	@Column
	private boolean isRecommended;

	@Column
	private int foodReviewCount;

	@Column
	private int foodOrderCount;
}

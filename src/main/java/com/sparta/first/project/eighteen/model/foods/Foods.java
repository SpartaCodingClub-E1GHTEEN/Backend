package com.sparta.first.project.eighteen.model.foods;

import java.util.List;
import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;
import com.sparta.first.project.eighteen.model.stores.Stores;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private String foodName;

	@Column
	private String foodDesc;

	@Column
	private int foodPrice;

	@Column
	private String foodImageUrl;

	@Column
	private FoodStatus foodStatus;

	@Column
	private boolean isRecommended;

	@Column
	private int foodReviewCount;

	@Column
	private int foodOrderCount;

	@OneToMany
	private List<FoodOptions> foodOptions;

	// 식당 ID
	@ManyToOne
	@JoinColumn(name = "store_id", referencedColumnName = "id")
	private Stores storeId;
}

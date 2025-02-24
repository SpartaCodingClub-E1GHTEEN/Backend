package com.sparta.first.project.eighteen.model.foods;

import java.util.List;
import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodUpdateRequestDto;
import com.sparta.first.project.eighteen.model.stores.Stores;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.Setter;
import lombok.ToString;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "p_foods")
public class Foods extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String foodName;

	@Column(length = 255)
	private String foodDesc;

	@Column(nullable = false)
	private int foodPrice;

	@Column(length = 255)
	private String foodImageUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FoodStatus foodStatus;

	@Column(nullable = false)
	private boolean isRecommended;

	@Column
	@Builder.Default
	private int foodOrderCount = 0;

	@Setter
	@OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FoodOptions> foodOptions;

	// 식당 ID
	@ManyToOne
	@JoinColumn(name = "store_id", referencedColumnName = "id", nullable = false)
	private Stores store;

	public void updateFood(FoodUpdateRequestDto requestDto, String foodDesc) {
		this.foodName = requestDto.getFoodName();
		this.foodDesc = foodDesc;
		this.foodPrice = requestDto.getFoodPrice();
		this.foodImageUrl = requestDto.getFoodImageUrl();
		this.foodStatus = requestDto.getFoodStatus();
		this.isRecommended = requestDto.getIsRecommended();
	}
}

package com.sparta.first.project.eighteen.model.orders;

import java.util.UUID;

import com.sparta.first.project.eighteen.model.foods.FoodOptions;

import jakarta.persistence.Entity;
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
@Table(name = "p_order_details_options")
public class OrderDetailsOptions {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "order_detail_id", referencedColumnName = "id")
	private OrderDetails orderDetail;

	@ManyToOne
	@JoinColumn(name = "food_option_id", referencedColumnName = "id")
	private FoodOptions foodOption;

	private String optionName;

	private int optionPrice;

}

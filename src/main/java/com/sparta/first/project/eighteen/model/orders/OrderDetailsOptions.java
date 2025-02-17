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
import lombok.Getter;

@Getter
@Entity
@Table(name = "p_order_details_options")
public class OrderDetailsOptions {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@ManyToOne
	@JoinColumn(name = "order_details_id", referencedColumnName = "id")
	private OrderDetails orderDetails;
	@ManyToOne
	@JoinColumn(name = "food_options_id", referencedColumnName = "id")
	private FoodOptions foodOptions;
	private String optionName;
	private int optionPrice;
}

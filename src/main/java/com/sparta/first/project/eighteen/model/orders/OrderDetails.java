package com.sparta.first.project.eighteen.model.orders;

import java.util.List;
import java.util.UUID;

import com.sparta.first.project.eighteen.model.foods.Foods;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "p_order_details")
public class OrderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Orders order;

	@ManyToOne
	@JoinColumn(name = "food_id", referencedColumnName = "id")
	private Foods food;

	private String foodName;

	private int foodPrice;

	private int amount;

	@OneToMany(mappedBy = "orderDetail")
	private List<OrderDetailsOptions> orderDetailsOptions;

	public void update(int amount, Foods food) {
		this.food = food;
		this.foodName = food.getFoodName();
		this.foodPrice = food.getFoodPrice();
		this.amount = amount;
	}
}

package com.sparta.first.project.eighteen.model.orders;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "p_order_details")
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	// @ManyToOne
	// @JoinColumn(name = "orderId", referencedColumnName = "id")
	// private Orders orders;
	private UUID orderId;
	// @ManyToOne
	// @JoinColumn(name = "foodId", referencedColumnName = "id")
	// private Foods foods;
	private UUID foodId;
	private String foodName;
	private int foodPrice;
	// @OneToMany
	// private List<OrderDetailsOptions> orderDetailsOptions;
}

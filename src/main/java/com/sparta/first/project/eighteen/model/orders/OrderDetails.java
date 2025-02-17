package com.sparta.first.project.eighteen.model.orders;

import java.util.List;
import java.util.UUID;

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
@Builder(access = AccessLevel.PRIVATE)
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

	// @ManyToOne
	// @JoinColumn(name = "food_id", referencedColumnName = "id")
	// private Foods food;
	private UUID foodId;

	private String foodName;

	private int foodPrice;

	@OneToMany
	private List<OrderDetailsOptions> orderDetailsOptions;

}

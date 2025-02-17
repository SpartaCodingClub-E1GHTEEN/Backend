package com.sparta.first.project.eighteen.model.orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Users;

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
@Table(name = "p_orders")
public class Orders extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "store_id", referencedColumnName = "id")
	private Stores storeId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users userId;

	private LocalDateTime orderTime;

	private boolean iStoreOrder;

	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;

	private String noteToStore;

	private String noteToDelivery;

	private int totalPrice;

	@OneToMany
	private List<OrderDetails> orderDetails;
}

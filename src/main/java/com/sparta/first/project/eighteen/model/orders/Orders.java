package com.sparta.first.project.eighteen.model.orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "p_order")
public class Orders extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	// @ManyToOne
	// @JoinColumn(referencedColumnName = "id", name = "storeId")
	// private Stores stores;
	private UUID storeId;
	// @ManyToOne
	// @JoinColumn(name = "userId")
	// private Users user;
	private UUID userId;
	private LocalDateTime orderTime;
	private boolean iStoreOrder;
	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;
	private String noteToStore;
	private String noteToDelivery;
	@OneToMany
	private List<OrderDetails> orderDetails;
}

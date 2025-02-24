package com.sparta.first.project.eighteen.model.orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderUpdateRequestDto;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "p_orders")
public class Orders extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", referencedColumnName = "id")
	private Stores store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users user;

	private LocalDateTime orderTime;

	private boolean isStoreOrder;

	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;

	private String noteToStore;

	private String noteToDelivery;

	private int totalPrice;

	private int totalCount;

	@OneToMany(mappedBy = "order")
	private List<OrderDetails> orderDetails;

	public void update(OrderUpdateRequestDto requestDto) {
		this.noteToStore = requestDto.getNoteToStore();
		this.noteToDelivery = requestDto.getNoteToDelivery();
		this.totalPrice = requestDto.getTotalPrice();
	}

	public void cancel() {
		this.status = OrderStatus.CANCELED;
	}

	public void changeStatus(OrderStatus status) {
		this.status = status;
	}
}

package com.sparta.first.project.eighteen.model.payments;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sparta.first.project.eighteen.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "p_payments")
public class Payments extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private UUID orderId;

	private PaymentStatus status;

	private String pgPaymentId;

	private LocalDateTime paymentTime;

	private PaymentMethod paymentMethod;

	private int amount;

	public void updateStatus(PaymentStatus status) {
		this.status = status;
	}
}

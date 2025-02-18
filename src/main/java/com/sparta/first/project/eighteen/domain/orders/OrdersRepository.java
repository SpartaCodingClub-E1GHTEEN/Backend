package com.sparta.first.project.eighteen.domain.orders;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.orders.Orders;

public interface OrdersRepository extends JpaRepository<Orders, UUID>, OrdersCustomRepository {
}

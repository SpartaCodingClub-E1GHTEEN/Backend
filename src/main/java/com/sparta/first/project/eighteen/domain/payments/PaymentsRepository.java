package com.sparta.first.project.eighteen.domain.payments;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.payments.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, UUID> {
	Optional<Payments> findByIdAndIsDeletedIsFalse(UUID id);
}

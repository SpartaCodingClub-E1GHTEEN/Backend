package com.sparta.first.project.eighteen.domain.foods;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.foods.Foods;

public interface FoodsRepository extends JpaRepository<Foods, UUID>, FoodsCustomRepository {
	Page<Foods> findByStore_IdAndFoodNameContainingIgnoreCase(UUID storeId, String keyword, Pageable pageable);

	Optional<Foods> findByIdAndIsDeletedIsFalse(UUID id);
}

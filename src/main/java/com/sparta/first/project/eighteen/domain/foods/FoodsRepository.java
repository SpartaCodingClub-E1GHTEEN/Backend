package com.sparta.first.project.eighteen.domain.foods;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.first.project.eighteen.model.foods.Foods;

public interface FoodsRepository extends JpaRepository<Foods, UUID> {

	@Query("SELECT f FROM Foods f WHERE f.store.id = :storeId " +
		"AND (:keyword IS NULL OR LOWER(f.foodName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		"OR LOWER(f.foodDesc) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
		"AND f.isDeleted = false")
	Page<Foods> searchFoods(UUID storeId, String keyword, PageRequest pageRequest);
}

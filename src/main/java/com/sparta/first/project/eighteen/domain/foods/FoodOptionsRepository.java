package com.sparta.first.project.eighteen.domain.foods;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.foods.FoodOptions;
import com.sparta.first.project.eighteen.model.foods.Foods;

public interface FoodOptionsRepository extends JpaRepository<FoodOptions, UUID> {
	List<FoodOptions> findByFoodAndIsDeletedFalse(Foods food);
}

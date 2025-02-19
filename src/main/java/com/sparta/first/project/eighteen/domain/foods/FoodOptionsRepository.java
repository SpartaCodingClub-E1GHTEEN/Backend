package com.sparta.first.project.eighteen.domain.foods;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.foods.FoodOptions;

public interface FoodOptionsRepository extends JpaRepository<FoodOptions, UUID> {
}

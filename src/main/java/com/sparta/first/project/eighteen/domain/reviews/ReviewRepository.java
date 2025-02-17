package com.sparta.first.project.eighteen.domain.reviews;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.reviews.Reviews;

public interface ReviewRepository extends JpaRepository<Reviews, UUID> {
}

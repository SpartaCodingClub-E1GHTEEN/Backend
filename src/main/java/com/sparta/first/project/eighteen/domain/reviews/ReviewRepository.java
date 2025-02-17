package com.sparta.first.project.eighteen.domain.reviews;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.reviews.Review;

public interface ReviewRepository extends JpaRepository<Review, String> {
}

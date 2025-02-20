package com.sparta.first.project.eighteen.domain.reviews;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewResponseDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewSearchDto;
import com.sparta.first.project.eighteen.model.users.Role;

public interface ReviewRepositoryCustom {
	Page<ReviewResponseDto> searchReviews(ReviewSearchDto searchDto, Pageable pageable, Role role, UUID storeId);
	Double getAvgReviewRatings(UUID storeId);
	long getCntReviews(UUID storeId);
}
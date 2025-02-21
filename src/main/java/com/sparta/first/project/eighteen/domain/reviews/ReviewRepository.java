package com.sparta.first.project.eighteen.domain.reviews;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.reviews.Reviews;
import com.sparta.first.project.eighteen.model.stores.Stores;

public interface ReviewRepository extends JpaRepository<Reviews, UUID>, ReviewRepositoryCustom {
	Page<Reviews> findAllByStoreId(UUID storeId, Pageable pageable);

	int countByStoreId(Stores storeId);

	List<Reviews> findReviewRatingByStoreId(Stores storeId);
}
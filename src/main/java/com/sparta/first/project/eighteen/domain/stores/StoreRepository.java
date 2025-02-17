package com.sparta.first.project.eighteen.domain.stores;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.stores.Store;

public interface StoreRepository extends JpaRepository<Store, UUID> {
}

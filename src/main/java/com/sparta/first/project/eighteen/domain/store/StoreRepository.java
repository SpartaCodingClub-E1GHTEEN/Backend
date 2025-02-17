package com.sparta.first.project.eighteen.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.stores.Store;

public interface StoreRepository extends JpaRepository<Store, String> {
}

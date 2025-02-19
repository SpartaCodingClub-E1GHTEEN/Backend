package com.sparta.first.project.eighteen.domain.stores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.first.project.eighteen.domain.stores.dtos.StoreListResponseDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreSearchDto;
import com.sparta.first.project.eighteen.model.users.Role;

public interface StoreRepositoryCustom {
	Page<StoreListResponseDto> searchStores(StoreSearchDto searchDto, Pageable pageable, Role role);
}
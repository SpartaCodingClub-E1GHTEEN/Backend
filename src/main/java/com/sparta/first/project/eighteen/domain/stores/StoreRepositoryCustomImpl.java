package com.sparta.first.project.eighteen.domain.stores;

import static com.sparta.first.project.eighteen.model.stores.QStores.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.first.project.eighteen.domain.reviews.ReviewRepository;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreListResponseDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreSearchDto;
import com.sparta.first.project.eighteen.model.stores.QStores;
import com.sparta.first.project.eighteen.model.stores.StoreCategory;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final ReviewRepository reviewRepository;

	/**
	 *
	 * @param searchDto
	 * @param pageable
	 * @param role
	 * @return
	 */
	@Override
	public Page<StoreListResponseDto> searchStores
		(StoreSearchDto searchDto, Pageable pageable, Role role) {

		List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);
		log.info(searchDto.getStoreRegion());

		QueryResults<Stores> results = queryFactory
			.selectFrom(stores)
			.where(
				confirmDeletedByRole(role) ,
				storeNameContains(searchDto.getStoreName()),
				storeRegion(searchDto.getStoreRegion()),
				storeCategory(searchDto.getStoreCategory()),
				deliveryPriceBetween(searchDto.getMinDeliveryPrice(), searchDto.getMaxDeliveryPrice())
			)
			.orderBy(orders.toArray(new OrderSpecifier[0]))
			.offset(pageable.getOffset())
			.limit(getPageSize(pageable.getPageSize(), pageable.getOffset()))
			.fetchResults();

		List<StoreListResponseDto> contents = invertToResponseDto(results);
		long total = results.getTotal();

		return new PageImpl<>(contents, pageable, total);
	}

	/**
	 * 리스트로 만들어 필터링
	 * @param results : 필터링할 쿼리 결과문
	 */
	private List<StoreListResponseDto> invertToResponseDto (QueryResults<Stores> results) {
		List<StoreListResponseDto> list = new ArrayList<>();

		// StoreRating을 어떻게 넣을지 ?
		for (Stores s : results.getResults()) {
			list.add(StoreListResponseDto.fromEntity(s));
		}

		return list;
	}

	/**
	 * 페이지에 가져올 데이터의 개수
	 * @param pageSize : 페이지에 가져올 데이터 개수
	 * @param offset : 페이지 수
	 * @return : 0페이지면 pagesize, 1페이지부터는 10개 반환
	 */
	private long getPageSize(long pageSize, long offset) {
		if (offset == 0) {
			return pageSize;
		} else {
			return 10;
		}
	}
	
	/**
	 * 식당 이름을 검색하는 경우
	 * @param storeName : 식당명 검색어
	 * @return : 검색어 포함 여부 반환
	 */
	private BooleanExpression storeNameContains(String storeName) {
		return storeName != null ? stores.storeName.contains(storeName) : null;
	}

	/**
	 * 식당 지역을 검색하는 경우
	 * @param storeRegion : 식당 지역
	 * @return : 지역과 일치하는지 여부 반환
	 */
	private BooleanExpression storeRegion(String storeRegion) {
		return storeRegion != null ? stores.storeRegion.eq(storeRegion) : null;
	}

	/**
	 * 식당 카테고리를 검색하는 경우
	 * @param storeCategory : 검색할 식당 카테고리
	 * @return : 카테고리와 일치하는지 여부 반환
	 */
	private BooleanExpression storeCategory(StoreCategory storeCategory) {
		return storeCategory != null ? stores.storeCategory.eq(storeCategory) : null;
	}

	/**
	 * 식당 배달팁의 최소, 최대값을 검색하는 경우
	 * @param minDeliveryPrice : 배달팁 최솟값
	 * @param maxDeliveryPrice : 배달팁 최댓값
	 * @return : 최소, 최대 범위 조건이 있는지 여부 반환
	 */
	private BooleanExpression deliveryPriceBetween(Integer minDeliveryPrice, Integer maxDeliveryPrice) {
		if ((minDeliveryPrice != null) && (maxDeliveryPrice != null)) {
			return stores.storeDeliveryPrice.between(minDeliveryPrice, maxDeliveryPrice);
		} else if (minDeliveryPrice != null) {
			return stores.storeDeliveryPrice.goe(minDeliveryPrice);
		} else if (maxDeliveryPrice != null) {
			return stores.storeDeliveryPrice.loe(maxDeliveryPrice);
		} else {
			return null;
		}
	}

	/**
	 * 권한에 따른 식당 리스트 확인
	 * @param role : 사용자 권한 확인
	 * @return : 권한에 따른 결과 리턴
	 */
	private BooleanExpression confirmDeletedByRole(Role role) {
		if (role.equals(Role.CUSTOMER)) {
			log.info("고객의 조회");
			// null or false인 경우만 반환 (삭제된 식당은 보이지 않음)
			return stores.isDeleted.isNull().or(stores.isDeleted.eq(false)); 
		} else {
			log.info("고객이 아닌 사람의 조회");
			// 아무 조건도 추가하지 않음으로써 모든 데이터 반환
			return null; 
		}
	}

	/**
	 * 정렬 조건을 기반으로 OrderSpecifier 리스트 생성
	 * @param pageable : 정렬 조건을 포함한 Pageable 객체
	 * @return : QueryDSL에서 사용할 OrderSpecifier 리스트
	 */
	private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
		List<OrderSpecifier<?>> orders = new ArrayList<>();

		// 정렬 기준이 존재한다면
		// pageable -> 클라이언트가 요청한 페이지 정보를 담고 있는 객체, 정렬 정보도 포함
		// sort -> 내부적으로 여러 개의 sort를 가짐
		if (pageable.getSort() != null) {
			// 정렬 정보 한 개씩 돌림
			for (Sort.Order sortOrder : pageable.getSort()) {
				orderSorting(sortOrder, orders);
			}
		}

		return orders;
	}

	/**
	 * Sort.Order기반의 정렬 조건을 정렬 리스트에 추가
	 * @param sortOrder : 정렬 정보(오름차순/내림차순)
	 * @param orders : 정렬 조건을 저장할 QueryDSL 리스트
	 */
	private void orderSorting(Sort.Order sortOrder, List<OrderSpecifier<?>> orders) {
		log.info("정렬 기준: " + sortOrder.getProperty() + " / " + "오름차순-내림차순: " + sortOrder.getDirection());
		com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;

		// 정렬 기준의 경우에 따라
		switch (sortOrder.getProperty()) {
			case "createdAt" :
				orders.add(new OrderSpecifier<>(direction, QStores.stores.createdAt));
				break;
			case "modifiedAt" :
				orders.add(new OrderSpecifier<>(direction, QStores.stores.modifiedAt));
				break;
			default :
				break;
		}
	}
}

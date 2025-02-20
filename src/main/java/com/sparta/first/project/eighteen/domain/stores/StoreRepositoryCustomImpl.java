package com.sparta.first.project.eighteen.domain.stores;

import static com.sparta.first.project.eighteen.model.stores.QStores.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreListResponseDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreResponseDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreSearchDto;
import com.sparta.first.project.eighteen.model.reviews.QReviews;
import com.sparta.first.project.eighteen.model.stores.QStores;
import com.sparta.first.project.eighteen.model.stores.StoreCategory;
import com.sparta.first.project.eighteen.model.users.Role;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	/**
	 * 식당 전체 조회
	 * @param searchDto : 조회할 정보
	 * @param pageable : pagenation 객체
	 * @param role : 사용자 역할
	 * @return : 조회한 식당 내용
	 */
	@Override
	public Page<StoreListResponseDto> searchStores(StoreSearchDto searchDto, Pageable pageable, Role role) {
		List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);
		log.info("pageNum : " + pageable.getOffset() + " pageSize : " + pageable.getPageSize());

		// 삭제된 리뷰는 계산에 포함되지 않음(역할에 관계없이)
		List<StoreListResponseDto> results = queryFactory
			.select(Projections.fields(
				StoreListResponseDto.class,
				Expressions.stringTemplate("CAST({0} AS string)", stores.id).as("id"),
				stores.storeName.as("storeName"),
				stores.storeRegion.as("storeRegion"),
				QReviews.reviews.reviewRating.avg().coalesce(0.0).as("storeRating"),
				stores.storeImgUrl.as("storeImgUrl"),
				stores.storeDeliveryPrice.as("storeDeliveryPrice")
			))
			.from(stores)
			.leftJoin(QReviews.reviews)
			.on(QReviews.reviews.storeId.id.eq(stores.id)
				.and(QReviews.reviews.isDeleted.eq(false)))
			.where(
				confirmDeletedByRole(role),
				storeNameContains(searchDto.getStoreName()),
				storeRegion(searchDto.getStoreRegion()),
				storeCategory(searchDto.getStoreCategory()),
				deliveryPriceBetween(searchDto.getMinDeliveryPrice(), searchDto.getMaxDeliveryPrice())
			)
			.orderBy(orders.toArray(new OrderSpecifier[0]))
			.groupBy(stores.id)
			.having(reviewRatingBetween(searchDto.getMinReviewRating(), searchDto.getMaxReviewRating()))
			.offset(pageable.getOffset())
			.limit(getPageSize(pageable.getPageSize(), pageable.getOffset()))
			.fetch();

		// 개수만 조회해서 가져오기
		long totalCount = totalPageElementCnt(role, searchDto);

		return new PageImpl<>(results, pageable, totalCount);
	}

	@Override
	public StoreResponseDto getOneStoreById(UUID storeId) {
		StoreResponseDto result = queryFactory
			.select(Projections.fields(
				StoreResponseDto.class,
				Expressions.stringTemplate("CAST({0} AS string)", stores.id).as("id"),
				stores.userId.username.as("userName"),
				stores.storeName.as("storeName"),
				stores.storeDesc.as("storeDesc"),
				stores.storeRegion.as("storeRegion"),
				stores.storeCategory.as("storeCategory"),
				QReviews.reviews.reviewRating.avg().coalesce(0.0).as("storeRating"),
				QReviews.reviews.count().as("storeReviewCnt"),
				stores.storeImgUrl.as("storeImgUrl"),
				stores.storeDeliveryPrice.as("storeDeliveryPrice"),
				Expressions.stringTemplate("CAST({0} AS string)", stores.createdAt).as("createdAt")
			))
			.from(stores)
			.leftJoin(QReviews.reviews)
			.on(QReviews.reviews.storeId.id.eq(stores.id)
				.and(QReviews.reviews.isDeleted.eq(false)))
			.where(stores.id.eq(storeId))
			.groupBy(stores.id)
			.fetchOne();

		if (result == null) {
			throw new EntityNotFoundException("해당 가게는 존재하지 않거나, 삭제되었습니다.");
		}

		return result;
	}

	/**
	 * 페이지에 가져올 데이터의 개수
	 * @param pageSize : 페이지에 가져올 데이터 개수
	 * @param offset : 페이지 수
	 * @return : 0페이지면 page size, 1페이지부터는 10개 반환
	 */
	private long getPageSize(long pageSize, long offset) {
		// 10, 30, 50개 중 하나의 값
		if (offset == 0) {
			return pageSize;
		} else {
			return 10;
		}
	}

	/**
	 * 페이지의 총 데이터 개수를 반환하는 메서드
	 * @param role : 역할
	 * @param searchDto : 데이터 검색 내용
	 * @return : 총 데이터 개수
	 */
	private long totalPageElementCnt(Role role, StoreSearchDto searchDto) {
		return queryFactory
			.select(stores.count())
			.from(stores)
			.leftJoin(QReviews.reviews)
			.on(QReviews.reviews.storeId.id.eq(stores.id)
				.and(QReviews.reviews.isDeleted.eq(false)))
			.where(
				confirmDeletedByRole(role),
				storeNameContains(searchDto.getStoreName()),
				storeRegion(searchDto.getStoreRegion()),
				storeCategory(searchDto.getStoreCategory()),
				deliveryPriceBetween(searchDto.getMinDeliveryPrice(), searchDto.getMaxDeliveryPrice())
			)
			.groupBy(stores.id)
			.having(reviewRatingBetween(searchDto.getMinReviewRating(), searchDto.getMaxReviewRating()))
			.fetchCount();
	}

	/**
	 * 리뷰 평점 계산
	 * @param minReviewRating : 리뷰 평점 최솟값
	 * @param maxReviewRating : 리뷰 평점 최댓값
	 * @return : 리뷰 평점 범위에 포함되는지 여부 반환
	 */
	private BooleanExpression reviewRatingBetween(Integer minReviewRating, Integer maxReviewRating) {
		NumberExpression<Double> avgRating = QReviews.reviews.reviewRating.avg().coalesce(0.0);

		if (minReviewRating != 0 && maxReviewRating != 0) {
			log.info("조건 둘다 있음");
			return avgRating.between(minReviewRating, maxReviewRating);
		} else if (minReviewRating != 0) {
			log.info("조건 최솟값만");
			return avgRating.goe(minReviewRating);
		} else if (maxReviewRating != 0) {
			log.info("조건 최댓값만");
			return avgRating.loe(maxReviewRating);
		} else {
			log.info("조건 없음");
			return Expressions.TRUE; // 아무 조건이 없을 경우 전체 데이터 반환
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
		if ((minDeliveryPrice != 0) && (maxDeliveryPrice != 0)) {
			return stores.storeDeliveryPrice.between(minDeliveryPrice, maxDeliveryPrice);
		} else if (minDeliveryPrice != 0) {
			return stores.storeDeliveryPrice.goe(minDeliveryPrice);
		} else if (maxDeliveryPrice != 0) {
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
		if (role.equals(Role.CUSTOMER) || role.equals(Role.RIDER) || role.equals(Role.OWNER)) {
			log.info("고객, 라이더, 식당 주인의 조회 - 삭제된 식당은 조회 불가");
			return stores.isDeleted.eq(false);
		} else {
			log.info("마스터, 매니저의 조회");
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

		/* 정렬 기준이 존재한다면
		   pageable -> 클라이언트가 요청한 페이지 정보를 담고 있는 객체, 정렬 정보도 포함
		   sort -> 내부적으로 여러 개의 sort를 가짐 */
		if (pageable.getSort() != null) {
			log.info("정렬 조건이 있어요");
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
				log.info("정렬 조건: createdAt / 오름차순/내림차순" + direction);
				orders.add(new OrderSpecifier<>(direction, QStores.stores.createdAt));
				break;
			case "modifiedAt" :
				log.info("정렬 조건: modifiedAt / 오름차순/내림차순" + direction);
				orders.add(new OrderSpecifier<>(direction, QStores.stores.modifiedAt));
				break;
			default :
				break;
		}
	}
}

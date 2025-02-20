package com.sparta.first.project.eighteen.domain.reviews;

import static com.sparta.first.project.eighteen.model.reviews.QReviews.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewResponseDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewSearchDto;
import com.sparta.first.project.eighteen.model.orders.QOrders;
import com.sparta.first.project.eighteen.model.reviews.QReviews;
import com.sparta.first.project.eighteen.model.users.QUsers;
import com.sparta.first.project.eighteen.model.users.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	/**
	 * 리뷰 검색 메서드
	 * @param searchDto : 검색할 내용
	 * @param pageable : pageable 객체
	 * @param role : 사용자의 역할
	 * @param storeId : 리뷰를 검색할 식당 ID
	 * @return : 리뷰들 반환
	 */
	@Override
	public Page<ReviewResponseDto> searchReviews(ReviewSearchDto searchDto, Pageable pageable, Role role, UUID storeId) {
		List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

		List<ReviewResponseDto> results = queryFactory
			.select(Projections.fields(
				ReviewResponseDto.class,
				Expressions.stringTemplate("CAST({0} AS string)", reviews.id).as("id"),
				Expressions.stringTemplate("CAST({0} AS string)", reviews.orderId.id).as("orderId"),
				reviews.usersId.userNickname.as("reviewNickname"),
				reviews.reviewContent.as("reviewContent"),
				reviews.reviewRating.as("reviewRating"),
				reviews.reviewImgUrl.as("reviewImgUrl"),
				Expressions.stringTemplate("CAST({0} AS string)", reviews.createdAt).as("createdAt")
			))
			.from(reviews)
			.leftJoin(reviews.orderId, QOrders.orders)
			.leftJoin(reviews.usersId, QUsers.users)
			.where(
				whichStore(storeId),
				reviewRating(searchDto.getReviewRatings()),
				containContents(searchDto.getReviewContent()),
				confirmRole(role)
			)
			.orderBy(orders.toArray(new OrderSpecifier[0]))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = getAllReviewCnt(storeId, searchDto);
		return new PageImpl<>(results, pageable, total);
	}

	/**
	 * 전체 리뷰 개수 반환
	 * @param storeId : 리뷰를 가져올 식당 ID
	 * @param searchDto : 검색 내용
	 * @return : 리뷰 개수 반환
	 */
	private long getAllReviewCnt(UUID storeId, ReviewSearchDto searchDto) {
		return queryFactory
			.select(reviews.count())
			.from(reviews)
			.leftJoin(reviews.orderId, QOrders.orders)
			.leftJoin(reviews.usersId, QUsers.users)
			.where(
				whichStore(storeId),
				reviewRating(searchDto.getReviewRatings()),
				containContents(searchDto.getReviewContent()),
				// 삭제되지 않은 리뷰의 개수만 반환
				reviews.isDeleted.eq(false)
			)
			.fetchOne();
	}

	/**
	 * 리뷰 평점 구하기 (삭제된 리뷰는 집계 X)
	 * @param storeId : 평점을 구할 식당 ID
	 * @return : 식당의 리뷰 평점
	 */
	@Override
	public Double getAvgReviewRatings(UUID storeId) {
		Double averageRating = queryFactory
			.select(reviews.reviewRating.avg())
			.from(reviews)
			.where(
				whichStore(storeId),
				reviews.isDeleted.eq(false))
			.fetchOne();

		return averageRating != null ? averageRating : 0.0;
	}

	/**
	 * 리뷰 개수 구하기 (삭제된 리뷰는 집계 X)
	 * @param storeId : 개수를 확인할 식당 ID
	 * @return : 식당의 리뷰 개수 반환
	 */
	@Override
	public long getCntReviews(UUID storeId) {
		long cntReviewRating = queryFactory
			.select(reviews.reviewRating.count())
			.from(reviews)
			.where(
				whichStore(storeId),
				reviews.isDeleted.eq(false))
			.fetchOne();

		return cntReviewRating;
	}

	/**
	 * 어떤 식당의 리뷰인지
	 * @param storeId : 리뷰를 검색할 식당의 ID
	 * @return : 식당에 해당하는지 여부 반환
	 */
	private BooleanExpression whichStore(UUID storeId) {
		return storeId != null ? reviews.storeId.id.eq(storeId) : null;
	}

	/**
	 * 리뷰 별점을 검색하는 경우
	 * @param ratingList : 선택한 별점이 담긴 리스트
	 * @return : 리스트 별점에 해당하는지 여부 반환
	 */
	private BooleanExpression reviewRating(List<Integer> ratingList) {
		if (ratingList == null || ratingList.isEmpty()) {
			return null;
		}
		return reviews.reviewRating.in(ratingList);
	}

	/**
	 * 리뷰 내용을 검색하는 경우
	 * @param content : 검색할 리뷰 내용
	 * @return : 리뷰 내용을 포함하는지 여부 반환
	 */
	private BooleanExpression containContents(String content) {
		return content != null ? reviews.reviewContent.contains(content) : null;
	}

	/**
	 * 권한에 따른 리뷰 리스트 확인
	 * @param role : 사용자 권한 확인
	 * @return : 권한에 따른 결과 리턴
	 */
	private BooleanExpression confirmRole(Role role) {
		if (role.equals(Role.CUSTOMER) || role.equals(Role.RIDER) || role.equals(Role.OWNER) || role == null) {
			// null or false인 경우만 반환 (삭제된 리뷰는 보이지 않음)
			return reviews.isDeleted.isNull().or(reviews.isDeleted.eq(false));
		} else {
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
				orders.add(new OrderSpecifier<>(direction, QReviews.reviews.createdAt));
				break;
			case "modifiedAt" :
				orders.add(new OrderSpecifier<>(direction, QReviews.reviews.modifiedAt));
				break;
			default :
				break;
		}
	}
}

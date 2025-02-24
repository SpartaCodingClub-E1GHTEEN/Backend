package com.sparta.first.project.eighteen.domain.reviews;

import static com.sparta.first.project.eighteen.model.reviews.QReviews.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewResponseDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewSearchDto;
import com.sparta.first.project.eighteen.model.orders.QOrderDetails;
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

		// 실행할 쿼리문
		List<Tuple> tuples = queryFactory
			.select(
				reviews.id,
				reviews.orderId.id,
				reviews.usersId.userNickname,
				QOrderDetails.orderDetails.foodName, // orderDetails와 조인해서 개별적으로 가져오기
				reviews.reviewContent,
				reviews.reviewRating,
				reviews.reviewImgUrl,
				reviews.createdAt
			)
			.from(reviews)
			.leftJoin(reviews.orderId, QOrders.orders)
			.leftJoin(reviews.usersId, QUsers.users)
			.leftJoin(reviews.orderDetails, QOrderDetails.orderDetails)
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

		// foodName이 여러개라면 Map으로 그룹핑
		Map<UUID, List<String>> reviewOrders = getFoodNames(tuples);

		// Dto로 변환 (중복은 제거)
		List<ReviewResponseDto> results = changeIntoResponseDtos(tuples, reviewOrders);

		// 전체 데이터 개수 반환
		Long total = null;
		try {
			total = getAllReviewCnt(storeId, searchDto);
		} catch (Exception e) {
			total = Long.valueOf(results.size());
		}

		return new PageImpl<>(results, pageable, total);
	}

	/**
	 * Review에 맞는 음식명들을 Map으로 반환
	 * @param tuples : tuples 리스트
	 * @return : 리뷰에 해당하는 음식명들을 Map으로 반환
	 */
	private Map<UUID, List<String>> getFoodNames(List<Tuple> tuples) {
		// 하나의 Review가 여러 개의 foodName을 가질 수 있는 형태 (리뷰 ID가 Key)
		Map<UUID, List<String>> reviewOrders = new HashMap<>();
		for (Tuple tuple : tuples) {
			// Tuple에서 값 추출
			UUID reviewId = tuple.get(reviews.id);
			String foodName = tuple.get(QOrderDetails.orderDetails.foodName);

			reviewOrders.computeIfAbsent(reviewId, k -> new ArrayList<>()).add(foodName);
		}

		return reviewOrders;
	}

	/**
	 * ResponseDto 리스트로 변환하는 메서드
	 * @param tuples : 튜플 리스트
	 * @param reviewOrders : 음식명들을 담은 Map 데이터
	 * @return : responseDto로 변환한 리스트
	 */
	private List<ReviewResponseDto> changeIntoResponseDtos(List<Tuple> tuples, Map<UUID, List<String>> reviewOrders) {
		return tuples.stream()
			.map(tuple -> new ReviewResponseDto(
				String.valueOf(tuple.get(reviews.id)),
				String.valueOf(tuple.get(reviews.orderId.id)),
				tuple.get(reviews.usersId.userNickname),
				reviewOrders.getOrDefault(tuple.get(reviews.id), new ArrayList<>()).toString(),
				tuple.get(reviews.reviewContent),
				tuple.get(reviews.reviewRating),
				tuple.get(reviews.reviewImgUrl),
				tuple.get(reviews.createdAt.stringValue())
			))
			.distinct()
			.collect(Collectors.toList());
	}

	/**
	 * 전체 리뷰 개수 반환
	 * @param storeId : 리뷰를 가져올 식당 ID
	 * @param searchDto : 검색 내용
	 * @return : 리뷰 개수 반환
	 */
	private Long getAllReviewCnt(UUID storeId, ReviewSearchDto searchDto) {
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

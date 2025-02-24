package com.sparta.first.project.eighteen.domain.foods;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodResponseDto;
import com.sparta.first.project.eighteen.domain.foods.dtos.FoodSearchRequestDto;
import com.sparta.first.project.eighteen.model.foods.Foods;
import com.sparta.first.project.eighteen.model.foods.QFoodOptions;
import com.sparta.first.project.eighteen.model.foods.QFoods;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FoodsRepositoryImpl implements FoodsCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QFoods foods = QFoods.foods;
	private final QFoodOptions foodOptions = QFoodOptions.foodOptions;

	@Override
	public Page<FoodResponseDto> findAllBySearchParam(FoodSearchRequestDto requestDto, Pageable pageable) {

		// ✅ 1. Foods와 FoodOptions 조인
		List<Foods> foodEntities = queryFactory
			.selectDistinct(foods)
			.from(foods)
			.leftJoin(foods.foodOptions, foodOptions).fetchJoin()
			.where(
				keywordContains(requestDto.getKeyword())
			)
			.orderBy(getSortedColumn(requestDto))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// ✅ 2. 조회된 엔티티를 DTO로 변환 (FoodResponseDto.fromEntity 사용)
		List<FoodResponseDto> results = foodEntities.stream()
			.map(FoodResponseDto::fromEntity)
			.toList();

		long total = queryFactory
			.select(foods.count())
			.from(foods)
			.where(keywordContains(requestDto.getKeyword()))
			.fetchOne();

		return new PageImpl<>(results, pageable, total);
	}

	private BooleanExpression keywordContains(String keyword) {
		return (keyword != null && !keyword.isEmpty()) ? foods.foodName.containsIgnoreCase(keyword) : null;
	}

	private OrderSpecifier<?> getSortedColumn(FoodSearchRequestDto requestDto) {
		PathBuilder<Foods> entityPath = new PathBuilder<>(Foods.class, "foods");

		return requestDto.getDirection().isAscending()
			? entityPath.getString(requestDto.getSortBy()).asc()
			: entityPath.getString(requestDto.getSortBy()).desc();
	}
}

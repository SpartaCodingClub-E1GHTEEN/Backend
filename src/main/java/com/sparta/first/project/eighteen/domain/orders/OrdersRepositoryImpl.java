package com.sparta.first.project.eighteen.domain.orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;

import com.sparta.first.project.eighteen.domain.orders.dtos.OrderResponseDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderSearchRequestDto;
import com.sparta.first.project.eighteen.model.orders.Orders;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersCustomRepository {

	private final EntityManager em;

	@Override
	public Page<OrderResponseDto> findAllBySearchParam(OrderSearchRequestDto requestDto) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<OrderResponseDto> cq = cb.createQuery(OrderResponseDto.class);
		Root<Orders> orders = cq.from(Orders.class);

		List<Predicate> criteria = new ArrayList<>();
		if (requestDto.getStoreId() != null) {
			criteria.add(cb.equal(orders.get("store").get("id"), requestDto.getStoreId()));
		}
		if (requestDto.getUserId() != null) {
			criteria.add(cb.equal(orders.get("user").get("id"), requestDto.getUserId()));
		}
		if (requestDto.getStatus() != null) {
			criteria.add(cb.equal(orders.get("status"), requestDto.getStatus()));
		}
		if (requestDto.getDateStart() != null) {
			criteria.add(cb.lessThanOrEqualTo(orders.<LocalDateTime>get("orderTime"), requestDto.getDateStart()));
		}
		if (requestDto.getDateEnd() != null) {
			criteria.add(cb.greaterThanOrEqualTo(orders.<LocalDateTime>get("orderTime"), requestDto.getDateStart()));
		}
		Sort sort = Sort.by(requestDto.getSort().getSortDirection(), requestDto.getSort().getProperties());
		cq.select(cb.construct(OrderResponseDto.class, orders))
			.where(cb.and(criteria.toArray(new Predicate[0])))
			.where(cb.equal(orders.get("isDeleted"), false))
			.orderBy(QueryUtils.toOrders(sort, orders, cb));

		List<OrderResponseDto> data = em.createQuery(cq)
			.setFirstResult(requestDto.getCurrentPage() * requestDto.getPageSize())
			.setMaxResults(requestDto.getPageSize())
			.getResultList();

		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		orders = countQuery.from(Orders.class);
		countQuery.select(cb.count(orders))
			.where(cb.and(criteria.toArray(new Predicate[0])));

		Long totalCount = em.createQuery(countQuery).getSingleResult();

		PageRequest pageRequest = PageRequest.of(requestDto.getCurrentPage(), requestDto.getPageSize(),
			Sort.by(requestDto.getSort().getSortDirection(), requestDto.getSort().getProperties()));

		return new PageImpl<>(data, pageRequest, totalCount);
	}
}

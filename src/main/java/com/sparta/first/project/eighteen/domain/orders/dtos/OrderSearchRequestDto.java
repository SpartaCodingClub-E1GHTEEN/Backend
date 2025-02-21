package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.sparta.first.project.eighteen.model.orders.OrderStatus;
import com.sparta.first.project.eighteen.model.orders.SortType;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSearchRequestDto {

	private String storeId;

	private String userId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDateTime dateStart;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDateTime dateEnd;

	private OrderStatus status;

	@Min(0)
	private Integer currentPage;

	@Min(1)
	private Integer pageSize;
	
	private SortType sort;
}

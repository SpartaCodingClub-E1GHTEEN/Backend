package com.sparta.first.project.eighteen.domain.orders.dtos;

import java.time.LocalDateTime;

import com.sparta.first.project.eighteen.model.orders.OrderStatus;
import com.sparta.first.project.eighteen.model.orders.SortType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSearchRequestDto {
    private String storeId;

    private String userId;

    private LocalDateTime dateStart;

    private LocalDateTime dateEnd;

    private OrderStatus status;

    private Integer currentPage;

    private Integer pageSize;

    private SortType sort;
}

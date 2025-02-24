package com.sparta.first.project.eighteen.model.orders;

public enum OrderStatus {
	PENDING,   // 주문 확인 중
	CONFIRMED, // 주문 접수
	COOKING,   // 조리중
	DELIVERING,// 배달중
	DELIVERED, // 배달 완료
	CANCELED;  // 주문 취소
}
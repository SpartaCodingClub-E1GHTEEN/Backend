package com.sparta.first.project.eighteen.domain.payments.dtos.naver;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import com.sparta.first.project.eighteen.model.orders.Orders;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NaverPayReservationRequestDto {

	@Builder.Default
	private String modelVersion = "2";

	private String merchantUserKey;

	@NotBlank
	private String merchantPayKey;

	@NotBlank
	private String productName;

	@NotBlank
	private int productCount;

	@Min(10)
	private int totalPayAmount;

	@NotBlank
	private String returnUrl;

	@Min(0)
	private int taxScopeAmount;

	@Min(0)
	private int taxExScopeAmount;

	private int environmentDepositAmount;

	private String purchaserName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String purchaserBirthday;

	@NotNull
	private List<NaverPayProductItem> productItems;

	private NaverPaySubMerchantInfo subMerchantInfo;

	public static NaverPayReservationRequestDto fromOrders(Orders order) {
		return NaverPayReservationRequestDto.builder()
			.merchantPayKey("mpaykey")
			.productName(order.getOrderDetails().get(0).getFoodName())
			.productCount(order.getTotalCount())
			.totalPayAmount(order.getTotalPrice())
			.returnUrl("https://test-m.pay.naver.com/z/payments/")
			.taxExScopeAmount(order.getTotalPrice())
			.productItems(
				order.getOrderDetails().stream()
					.map(detail -> NaverPayProductItem.fromOrderDetail(detail))
					.collect(Collectors.toList()))
			.build();
	}

}

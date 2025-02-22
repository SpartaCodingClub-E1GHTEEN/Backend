package com.sparta.first.project.eighteen.domain.payments.dtos.naver;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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

}

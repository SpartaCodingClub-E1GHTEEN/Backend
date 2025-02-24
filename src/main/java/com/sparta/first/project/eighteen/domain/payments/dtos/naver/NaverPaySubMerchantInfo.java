package com.sparta.first.project.eighteen.domain.payments.dtos.naver;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class NaverPaySubMerchantInfo {

	@NotBlank
	private String subMerchantName;

	@NotBlank
	private String subMerchantId;

	@NotBlank
	private String subMerchantBusinessNo;

	@NotBlank
	private String subMerchantPayId;

	@NotBlank
	private String subMerchantTelephoneNo;

	@NotBlank
	private String subMerchantCustomerServiceUrl;
}

package com.sparta.first.project.eighteen.domain.payments.dtos.naver;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class NaverPayProductItem {

	@NotBlank
	private String categoryType;

	@NotBlank
	private String categoryId;

	@NotBlank
	private String uid;

	@NotBlank
	private String name;

	private String payReferrer;

	@Min(1)
	@Max(999999)
	private int count;
}

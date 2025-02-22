package com.sparta.first.project.eighteen.utils;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sparta.first.project.eighteen.common.exception.PaymentException;
import com.sparta.first.project.eighteen.domain.payments.dtos.naver.NaverPayApiResponse;
import com.sparta.first.project.eighteen.domain.payments.dtos.naver.NaverPayReservationRequestDto;

@Component
public class NaverPayApiClient {

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${pay.naver.merchant-key}")
	String merchantKey;

	@Value("${pay.naver.client-id}")
	String clientId;

	@Value("${pay.naver.client-secret}")
	String clientSecret;

	@Value("${pay.naver.chain-id}")
	String chainId;

	@Value("${pay.naver.api-domain}")
	String apiDomain;

	@Value("${pay.naver.partner-id}")
	String partnerId;

	String reserveUri = "/naverpay/payments/v2/reserve";

	@Value("${pay.naver.return-url}")
	String returnUrl;

	//결제창 url을 반환
	public String requestReservationId(NaverPayReservationRequestDto requestDto) {
		String idempotencyKey = String.valueOf(UUID.randomUUID());
		// 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", clientId);
		headers.set("X-Naver-Client-Secret", clientSecret);
		headers.set("X-NaverPay-Chain-Id", chainId);
		headers.set("X-NaverPay-Idempotency-Key", idempotencyKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<NaverPayReservationRequestDto> entity = new HttpEntity<>(requestDto, headers);

		ResponseEntity<NaverPayApiResponse> response = restTemplate.exchange(
			apiDomain + partnerId + reserveUri,
			HttpMethod.POST,
			entity,
			NaverPayApiResponse.class
		);

		// 응답 아이디 파싱
		NaverPayApiResponse<Map<String, String>> responseBody = response.getBody();
		if (responseBody.getCode().equals("Success")) {
			String reserveId = responseBody.getBody().get("reserveId");
			return returnUrl += reserveId;
		} else {
			throw new PaymentException.NaverPayReservationFailed(responseBody.getMessage());
		}
	}
}

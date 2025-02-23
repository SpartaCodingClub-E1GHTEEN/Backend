package com.sparta.first.project.eighteen.domain.payments;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.common.security.UserDetailsImpl;
import com.sparta.first.project.eighteen.domain.payments.dtos.PaymentCreateRequestDto;
import com.sparta.first.project.eighteen.domain.payments.dtos.PaymentResponseDto;
import com.sparta.first.project.eighteen.model.payments.PaymentMethod;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@RestController
public class PaymentController {

	private final PaymentService paymentService;

	@GetMapping
	public ResponseEntity<ApiResponse<String>> requestPayment(@RequestParam String orderId,
		@RequestParam PaymentMethod paymentMethod,
		@AuthenticationPrincipal UserDetailsImpl user) {
		String requestUrl = paymentService.requestPayment(orderId, paymentMethod);
		return ResponseEntity.ok(ApiResponse.ok("결제가 요청되었습니다.", requestUrl));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<PaymentResponseDto>> createPayment(
		@Validated @RequestBody PaymentCreateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl user) {
		PaymentResponseDto responseDto = paymentService.createPayment(requestDto);
		return ResponseEntity.ok(ApiResponse.ok("결제 내역이 생성되었습니다.", responseDto));
	}
}

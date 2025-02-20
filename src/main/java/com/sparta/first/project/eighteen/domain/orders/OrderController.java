package com.sparta.first.project.eighteen.domain.orders;

import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.common.security.UserDetailsImpl;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderCreateRequestDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderResponseDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderSearchRequestDto;
import com.sparta.first.project.eighteen.domain.orders.dtos.OrderUpdateRequestDto;
import com.sparta.first.project.eighteen.model.orders.OrderStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@RestController
public class OrderController {

	private final OrderService orderService;

	/**
	 * 주문 생성
	 *
	 * @param requestDto : 주문 정보
	 * @param user : 로그인한 사용자
	 * @return  :
	 */
	@PreAuthorize("hasAnyRole('OWNER','CUSTOMER')")
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createOrder(@RequestBody OrderCreateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl user) {
		orderService.createOrder(requestDto, user.getUserUUID());
		return ResponseEntity.ok(ApiResponse.ok("주문이 완료되었습니다.", null));
	}

	/**
	 * 주문 조회
	 *
	 * @param id : 조회할 주문 ID
	 * @return requestDto : 주문 내용
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<OrderResponseDto>> readOrder(@PathVariable String id) {
		OrderResponseDto responseDto = orderService.readOrder(id);
		return ResponseEntity.ok(ApiResponse.ok("주문을 조회했습니다.", responseDto));
	}

	/**
	 * 주문 목록 조회
	 *
	 * @param requestDto : 주문 필터, 정렬, 페이징 정보
	 * @return  : 주문 정보
	 */
	@PreAuthorize("hasAnyRole('MASTER','MANAGER','OWNER')")
	@GetMapping
	public ResponseEntity<ApiResponse<PagedModel<OrderResponseDto>>> searchOrder(
		@ModelAttribute OrderSearchRequestDto requestDto) {
		PagedModel<OrderResponseDto> responseDto = orderService.searchOrder(requestDto);
		return ResponseEntity.ok(ApiResponse.ok("주문 목록을 조회했습니다", responseDto));
	}

	/**
	 * 주문 수정
	 *
	 * @param id : 조회할 주문 ID
	 * @param requestDto : 주문 수정 내용
	 * @return  : 주문 정보
	 */
	@PreAuthorize("hasRole('MANAGER')")
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(@PathVariable String id,
		@RequestBody OrderUpdateRequestDto requestDto) {
		OrderResponseDto responseDto = orderService.updateOrder(requestDto, id);
		return ResponseEntity.ok(ApiResponse.ok("주문을 수정했습니다.", responseDto));
	}

	/**
	 * 주문 취소
	 *
	 * @param id : 조회할 주문 ID
	 * @return  : 주문 정보
	 */
	@PreAuthorize("hasAnyRole('MANAGER','OWNER', 'CUSTOMER')")
	@DeleteMapping("/{id}/cancel")
	public ResponseEntity<ApiResponse<OrderResponseDto>> cancelOrder(@PathVariable String id) {
		OrderResponseDto responseDto = orderService.cancelOrder(id);
		return ResponseEntity.ok(ApiResponse.ok("주문을 취소했습니다.", responseDto));
	}

	/**
	 * 주문 삭제(MASTER)
	 *
	 * @param id : 조회할 주문 ID
	 * @return  :
	 */
	@PreAuthorize("hasRole('MASTER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable String id,
		@AuthenticationPrincipal UserDetailsImpl user) {
		orderService.deleteOrder(id, user.getUserUUID().toString());
		return ResponseEntity.ok(ApiResponse.ok("주문을 삭제했습니다.", null));
	}

	/**
	 * 주문 상태변경 (OWNER/RIDER)
	 *
	 * @param id : 조회할 주문 ID
	 * @param status : 주문 변경 상태
	 * @return  : 주문 정보
	 */
	@PreAuthorize("hasAnyRole('OWNER','RIDER')")
	@PatchMapping("/{id}/{status}")
	public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrderStatus(@PathVariable String id,
		@PathVariable OrderStatus status) {
		OrderResponseDto responseDto = orderService.updateOrderStatus(id, status);
		return ResponseEntity.ok(ApiResponse.ok("주문 상태가 변경되었습니다.", responseDto));
	}
}

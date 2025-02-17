package com.sparta.first.project.eighteen.domain.reviews;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewCreateRequestDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewResponseDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewUpdateRequestDto;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ReviewController {

	/**
	 * 리뷰 생성
	 * // * @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @param requestDto : 리뷰 정보
	 * @return : 생성한 리뷰 내용
	 */
	@PostMapping("/reviews")
	public ResponseEntity<ApiResponse<ReviewResponseDto>> createReview(
		// @AuthenticationPrincipal UserDetails userDetails,
		@RequestBody @Valid ReviewCreateRequestDto requestDto) {

		// 권한 확인하기
		// userDetails.getAuthorities();
		return ResponseEntity.ok(ApiResponse.ok("성공", new ReviewResponseDto(requestDto)));
	}

	/**
	 * 리뷰 전체 조회
	 * @param storeId : 리뷰를 조회할 가게 ID
	 * @return : 조회한 가게의 리뷰들
	 */
	@GetMapping("/stores/{storeId}/reviews")
	public ResponseEntity<ApiResponse<Page<ReviewResponseDto>>> getAllReviews(
		@PathVariable String storeId, Pageable pageable) {
		log.info("ReviewController - getAllReviews | storeId - " + storeId);
		return ResponseEntity.ok(ApiResponse.ok("성공", null));
	}

	/**
	 * 리뷰 단건 조회
	 * @param reviewId : 조회할 리뷰의 ID
	 * @return : 조회한 리뷰의 내용
	 */
	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse<ReviewResponseDto>> getOneReview(
		@PathVariable String reviewId) {
		log.info("ReviewController - getOneReview | storeId - " + reviewId);
		return ResponseEntity.ok(ApiResponse.ok("성공", new ReviewResponseDto()));
	}

	/**
	 * 리뷰 수정
	 * // @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @param reviewId : 수정할 리뷰의 ID
	 * @param requestDto : 수정할 리뷰 내용
	 * @return : 수정한 리뷰
	 */
	@PutMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReview(
		// @AuthenticationPrincipal UserDetails userDetails,
		@PathVariable String reviewId,
		@RequestBody @Valid ReviewUpdateRequestDto requestDto) {

		// 권한 확인하기
		// userDetails.getAuthorities();
		log.info("ReviewController - updateReview | reviewId - " + reviewId);
		return ResponseEntity.ok(ApiResponse.ok("성공", new ReviewResponseDto(requestDto)));
	}

	/**
	 * 리뷰 삭제
	 * // @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @param reviewId : 삭제할 리뷰의 ID
	 * @return : 상태코드 및 메시지 반환
	 */
	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse> deleteReview(
		// @AuthenticationPrincipal UserDetails userDetails,
		@PathVariable String reviewId) {

		// 권한 확인하기
		// userDetails.getAuthorities();
		log.info("ReviewController - deleteReview | reviewId - " + reviewId);
		return ResponseEntity.ok(ApiResponse.ok("성공", null));
	}

}
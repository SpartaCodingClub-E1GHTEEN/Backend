package com.sparta.first.project.eighteen.domain.reviews;

import java.util.UUID;

import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.sparta.first.project.eighteen.common.constants.Constant;
import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.common.exception.BaseException;
import com.sparta.first.project.eighteen.common.security.UserDetailsImpl;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewCreateRequestDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewResponseDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewSearchDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewUpdateRequestDto;
import com.sparta.first.project.eighteen.model.users.Role;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {

	private final ReviewService reviewService;

	/**
	 * 리뷰 생성
	 * @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @param requestDto : 리뷰 정보
	 * @return : 생성한 리뷰 내용
	 */
	@PostMapping("/reviews")
	public ResponseEntity<ApiResponse<ReviewResponseDto>> createReview(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestParam(value = "reviewImage") MultipartFile reviewImage,
		@ModelAttribute @Valid ReviewCreateRequestDto requestDto) {
		ReviewResponseDto responseDto = reviewService.createReview(userDetails.getUserUUID(), requestDto, reviewImage);
		return ResponseEntity.ok(ApiResponse.ok("성공", responseDto));
	}

	/**
	 * 리뷰 전체 조회
	 * @param storeId : 리뷰를 조회할 가게 ID
	 * @return : 조회한 가게의 리뷰들
	 */
	@GetMapping("/stores/{storeId}/reviews")
	public ResponseEntity<ApiResponse<PagedModel<ReviewResponseDto>>> getAllReviews(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID storeId, @ModelAttribute ReviewSearchDto searchDto) {
		log.info("ReviewController - getAllReviews | storeId - " + storeId);
		PagedModel<ReviewResponseDto> responseDtos = reviewService.getAllReviews(storeId, searchDto, userDetails.getUserUUID());
		return ResponseEntity.ok(ApiResponse.ok("성공", responseDtos));
	}

	/**
	 * 리뷰 단건 조회
	 * @param reviewId : 조회할 리뷰의 ID
	 * @return : 조회한 리뷰의 내용
	 */
	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse<ReviewResponseDto>> getOneReview(
		@PathVariable UUID reviewId) {
		log.info("ReviewController - getOneReview | storeId - " + reviewId);
		ReviewResponseDto responseDto = reviewService.getOneReview(reviewId);
		return ResponseEntity.ok(ApiResponse.ok("성공", responseDto));
	}

	/**
	 * 리뷰 수정
	 * @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @param reviewId : 수정할 리뷰의 ID
	 * @param requestDto : 수정할 리뷰 내용
	 * @return : 수정한 리뷰
	 */
	@PutMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReview(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID reviewId,
		@RequestParam(value = "reviewImage") MultipartFile reviewImage,
		@ModelAttribute @Valid ReviewUpdateRequestDto requestDto) {

		log.info("ReviewController - updateReview | reviewId - " + reviewId);

		Role role = reviewService.findUserRole(userDetails.getUserUUID());

		if (role.equals(Role.OWNER) || role.equals(Role.RIDER)) {
			throw new BaseException("리뷰를 수정할 권한이 없습니다", Constant.Code.REVIEW_ERROR, HttpStatus.BAD_GATEWAY);
		}

		ReviewResponseDto responseDto = reviewService.updateReview(userDetails.getUserUUID(), reviewId, requestDto, reviewImage);
		return ResponseEntity.ok(ApiResponse.ok("성공", responseDto));
	}

	/**
	 * 리뷰 삭제
	 * @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @param reviewId : 삭제할 리뷰의 ID
	 * @return : 상태코드 및 메시지 반환
	 */
	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse> deleteReview(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID reviewId) {

		log.info("ReviewController - deleteReview | reviewId - " + reviewId);

		Role role = reviewService.findUserRole(userDetails.getUserUUID());

		if (role.equals(Role.OWNER) || role.equals(Role.RIDER)) {
			throw new BaseException("리뷰를 삭제할 권한이 없습니다", Constant.Code.REVIEW_ERROR, HttpStatus.BAD_GATEWAY);
		}

		ApiResponse apiResponse = reviewService.deleteReview(userDetails.getUserUUID(), reviewId);
		return ResponseEntity.ok(ApiResponse.ok("성공", apiResponse));
	}

}
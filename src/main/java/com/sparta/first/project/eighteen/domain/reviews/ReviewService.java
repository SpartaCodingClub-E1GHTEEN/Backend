package com.sparta.first.project.eighteen.domain.reviews;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.first.project.eighteen.common.constants.Constant;
import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.common.exception.BaseException;
import com.sparta.first.project.eighteen.common.exception.OrderException;
import com.sparta.first.project.eighteen.common.exception.StoreException;
import com.sparta.first.project.eighteen.common.exception.UserException;
import com.sparta.first.project.eighteen.domain.orders.OrdersRepository;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewCreateRequestDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewResponseDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewSearchDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewUpdateRequestDto;
import com.sparta.first.project.eighteen.domain.stores.StoreRepository;
import com.sparta.first.project.eighteen.domain.users.UserRepository;
import com.sparta.first.project.eighteen.model.orders.OrderDetails;
import com.sparta.first.project.eighteen.model.orders.OrderStatus;
import com.sparta.first.project.eighteen.model.orders.Orders;
import com.sparta.first.project.eighteen.model.reviews.Reviews;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.Users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	private final UserRepository userRepository;
	private final StoreRepository storeRepository;
	private final OrdersRepository ordersRepository;
	private final ReviewRepository reviewRepository;

	/**
	 * 리뷰 작성
	 * @param userId : 리뷰를 작성하고자 하는 사용자명
	 * @param requestDto : 리뷰 내용
	 * @return : 작성한 리뷰
	 */
	public ReviewResponseDto createReview(UUID userId, ReviewCreateRequestDto requestDto) {
		// 주문 탐색
		Orders order = findOrders(requestDto.getOrderId());
		Stores store = findStore(order.getStore().getId());
		Users user = findReviewerById(userId);

		// 주문 상태 확인
		if (!order.getStatus().equals(OrderStatus.DELIVERED)) {
			throw new BaseException("완료된 주문이 아닙니다.", Constant.Code.REVIEW_ERROR, HttpStatus.BAD_REQUEST);
		}

		// 해당 주문건을 주문한 유저인지
		if (!order.getUser().getUserId().equals(user.getUserId())) {
			throw new BaseException("사용자의 주문 내역이 아닙니다.", Constant.Code.REVIEW_ERROR, HttpStatus.BAD_REQUEST);
		}

		List<OrderDetails> orderDetails = order.getOrderDetails();

		Reviews review = requestDto.toEntity(store, order, user, orderDetails);
		Reviews newReview = reviewRepository.save(review);
		return ReviewResponseDto.fromEntity(newReview);
	}

	/**
	 * 식당에 있는 리뷰들 전체 조회
	 * @param storeId : 조회할 식당 ID
	 * @param searchDto : 검색할 정보
	 * @param userId : 검색하는 사용자
	 * @return : 조회한 리뷰들
	 */
	@Transactional(readOnly = true)
	public PagedModel<ReviewResponseDto> getAllReviews(UUID storeId, ReviewSearchDto searchDto, UUID userId) {
		Role role = Role.CUSTOMER;

		if (userId != null) {
			role = findUserRole(userId);
		}

		try {
			log.info(searchDto.getPage() + searchDto.getSize() + searchDto.getSortBy() + searchDto.getDirection());

			Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(),
				searchDto.getDirection(), searchDto.getSortBy());

			Page<ReviewResponseDto> reviews = reviewRepository.searchReviews(
				searchDto, pageable, role, storeId);

			return new PagedModel<>(reviews);
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), Constant.Code.REVIEW_ERROR, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 리뷰 단건 조회
	 * @param reviewId : 조회할 리뷰 ID
	 * @return : 조회한 리뷰
	 */
	@Transactional(readOnly = true)
	public ReviewResponseDto getOneReview(UUID reviewId) {
		Reviews review = findReview(reviewId);
		return ReviewResponseDto.fromEntity(review);
	}

	/**
	 * 리뷰 수정
	 * @param userId : 수정하려는 사용자명
	 * @param reviewId : 수정할 리뷰 ID
	 * @param requestDto : 수정할 리뷰 내용
	 * @return : 수정한 리뷰
	 */
	@Transactional
	public ReviewResponseDto updateReview(UUID userId, UUID reviewId, ReviewUpdateRequestDto requestDto) {
		Reviews review = findReview(reviewId);
		Users user = findReviewerById(userId);

		// 리뷰 작성자거나 마스터나 매니저만 가능
		if (user.getRole().equals(Role.CUSTOMER) &&
			!review.getUsersId().getUserId().equals(user.getUserId())) {
			throw new BaseException("리뷰 작성자가 아닙니다.", Constant.Code.REVIEW_ERROR, HttpStatus.NOT_FOUND.FORBIDDEN);
		}

		Reviews updateReview = requestDto.toEntity();
		review.updateReview(updateReview);
		return ReviewResponseDto.fromEntity(review);
	}

	/**
	 * 리뷰 삭제
	 * @param userId : 리뷰를 삭제할 사용자명
	 * @param reviewId : 삭제할 리뷰 ID
	 * @return : 삭제한 리뷰 상태
	 */
	@Transactional
	public ApiResponse deleteReview(UUID userId, UUID reviewId) {
		Reviews review = findReview(reviewId);
		Users user = findReviewerById(userId);

		// 리뷰 작성자거나 마스터나 매니저만 가능
		if (user.getRole().equals(Role.CUSTOMER) &&
			!review.getUsersId().getUserId().equals(user.getUserId())) {
			throw new BaseException("리뷰 작성자가 아닙니다.", Constant.Code.REVIEW_ERROR, HttpStatus.NOT_FOUND.FORBIDDEN);
		}

		review.delete(true, user.getUserId().toString());
		reviewRepository.save(review);
		log.info("삭제 여부 : " + review.getIsDeleted());
		return ApiResponse.ok("삭제 성공", "식당명 : " + review.getReviewContent());
	}

	/**
	 * 리뷰 탐색
	 * @param reviewId : 조회할 리뷰의 ID
	 * @return : 조회한 리뷰
	 */
	public Reviews findReview(UUID reviewId) {
		return reviewRepository.findById(reviewId).filter(r -> !r.getIsDeleted())
			.orElseThrow(() -> new BaseException("존재하지 않거나 삭제된 리뷰", Constant.Code.REVIEW_ERROR, HttpStatus.NOT_FOUND));
	}

	/**
	 * 식당 탐색
	 * @param storeId : 조회할 식당의 ID
	 * @return : 조회한 식당
	 */
	public Stores findStore(UUID storeId) {
		return storeRepository.findById(storeId).filter(s -> !s.getIsDeleted())
			.orElseThrow(() -> new StoreException.StoreNotFound());
	}

	/**
	 * 주문 탐색
	 * @param orderId : 조회할 주문의 ID
	 * @return : 조회한 주문
	 */
	public Orders findOrders(UUID orderId) {
		return ordersRepository.findById(orderId).filter(o -> !o.getIsDeleted())
			.orElseThrow(() -> new OrderException.OrderNotFound());
	}

	/**
	 * 리뷰 작성자 탐색
	 * @param userId : 리뷰를 작성하거나 수정할 사용자
	 * @return : 조회한 사용자
	 */
	public Users findReviewerById(UUID userId) {
		return userRepository.findById(userId).orElseThrow(
			() -> new UserException.UserNotFound());
	}

	/**
	 * 사용자 권한 탐색
	 * @param userId : 식당을 차릴 사용자명
	 * @return : 조회한 사용자
	 */
	public Role findUserRole(UUID userId) {
		return userRepository.findById(userId).orElseThrow(
			() -> new UserException.UserNotFound()).getRole();
	}

}

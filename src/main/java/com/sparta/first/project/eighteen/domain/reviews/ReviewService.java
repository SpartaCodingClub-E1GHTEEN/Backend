package com.sparta.first.project.eighteen.domain.reviews;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.orders.OrdersRepository;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewCreateRequestDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewResponseDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewSearchDto;
import com.sparta.first.project.eighteen.domain.reviews.dtos.ReviewUpdateRequestDto;
import com.sparta.first.project.eighteen.domain.stores.StoreRepository;
import com.sparta.first.project.eighteen.domain.users.UserRepository;
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
	 * @param username : 리뷰를 작성하고자 하는 사용자명
	 * @param requestDto : 리뷰 내용
	 * @return : 작성한 리뷰
	 */
	public ReviewResponseDto createReview(String username, ReviewCreateRequestDto requestDto) {
		// 주문 탐색
		Orders order = findOrders(requestDto.getOrderId());
		Stores store = findStore(order.getStore().getId());
		Users user = findReviewer(username);

		// 주문 상태 확인
		if (!order.getStatus().equals(OrderStatus.DELIVERED)) {
			throw new IllegalArgumentException("완료된 주문이 아닙니다.");
		}

		// 해당 주문건을 주문한 유저인지
		if (!order.getUser().getUserId().equals(user.getUserId())) {
			throw new IllegalArgumentException("사용자의 주문 내역이 아닙니다.");
		}

		Reviews review = requestDto.toEntity(store, order, user);
		Reviews newReview =reviewRepository.save(review);
		return ReviewResponseDto.fromEntity(newReview);
	}

	/**
	 * 식당에 있는 리뷰들 조회
	 *
	 * @param storeId   : 조회할 식당 ID
	 * @param searchDto
	 * @param username
	 * @return : 조회한 리뷰들
	 */
	public PagedModel<ReviewResponseDto> getAllReviews(UUID storeId, ReviewSearchDto searchDto, String username) {
		Role role = Role.CUSTOMER;
		if (username != null) {
			role = userRepository.findByUsername(username).orElseThrow().getRole();
		}

		Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(),
			searchDto.getDirection(), searchDto.getSortBy());

		Page<ReviewResponseDto> reviews = reviewRepository.searchReviews(
			searchDto, pageable, role, storeId);

		return new PagedModel<>(reviews);


/*		Pageable pageable = PageRequest.of(0, 10);
		List<ReviewResponseDto> reviews = reviewRepository.findAllByStore(storeId, pageable);
		return reviews;*/
	}

	/**
	 * 리뷰 단건 조회
	 * @param reviewId : 조회할 리뷰 ID
	 * @return : 조회한 리뷰
	 */
	public ReviewResponseDto getOneReview(UUID reviewId) {
		ReviewResponseDto responseDto = reviewRepository.getOneReviewToDto(reviewId);
		return responseDto;
	}

	/**
	 * 리뷰 수정
	 * @param username : 수정하려는 사용자명
	 * @param reviewId : 수정할 리뷰 ID
	 * @param requestDto : 수정할 리뷰 내용
	 * @return : 수정한 리뷰
	 */
	@Transactional
	public ReviewResponseDto updateReview(String username, UUID reviewId, ReviewUpdateRequestDto requestDto) {
		Reviews review = findReview(reviewId);
		Users user = findReviewer(username);

		if (user.getUsername().equals(Role.CUSTOMER) &&
			!review.getUsersId().getUserId().equals(user.getUserId())) {
			throw new IllegalArgumentException("리뷰 작성자가 아닙니다.");
		}

		Reviews updateReview = requestDto.toEntity();
		review.updateReview(updateReview);
		return ReviewResponseDto.fromEntity(review);
	}

	/**
	 * 리뷰 삭제
	 * @param username : 리뷰를 삭제할 사용자명
	 * @param reviewId : 삭제할 리뷰 ID
	 * @return : 삭제한 리뷰 상태
	 */
	@Transactional
	public ApiResponse deleteReview(String username, UUID reviewId) {
		Reviews review = findReview(reviewId);
		Users user = findReviewer(username);

		if (user.getUsername().equals(Role.CUSTOMER) &&
			!review.getUsersId().getUserId().equals(user.getUserId())) {
			throw new IllegalArgumentException("리뷰 작성자가 아닙니다.");
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
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 삭제된 리뷰"));
	}

	/**
	 * 식당 탐색
	 * @param storeId : 조회할 식당의 ID
	 * @return : 조회한 식당
	 */
	public Stores findStore(UUID storeId) {
		return storeRepository.findById(storeId).filter(s -> !s.getIsDeleted())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 삭제된 식당"));
	}

	/**
	 * 주문 탐색
	 * @param orderId : 조회할 주문의 ID
	 * @return : 조회한 주문
	 */
	public Orders findOrders(UUID orderId) {
		return ordersRepository.findById(orderId).filter(o -> !o.getIsDeleted())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 삭제된 식당"));
	}

	/**
	 * 식당 주인 탐색
	 * @param username : 식당을 차릴 사용자명
	 * @return : 조회한 사용자
	 */
	public Users findReviewer(String username) {
		return userRepository.findByUsername(username).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 유저"));
	}

}

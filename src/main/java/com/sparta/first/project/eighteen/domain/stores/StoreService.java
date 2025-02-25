package com.sparta.first.project.eighteen.domain.stores;

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
import com.sparta.first.project.eighteen.common.exception.StoreException;
import com.sparta.first.project.eighteen.domain.reviews.ReviewRepository;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreListResponseDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreCreateRequestDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreResponseDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreSearchDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreUpdateRequestDto;
import com.sparta.first.project.eighteen.domain.users.UserRepository;
import com.sparta.first.project.eighteen.model.stores.Stores;
import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.Users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;

	/**
	 * 식당 생성
	 * @param storeCreateRequestDto : 생성할 식당의 내용
	 * @return : 생성 완료한 식당의 정보
	 */
	public StoreResponseDto createStore(StoreCreateRequestDto storeCreateRequestDto) {
		// 식당을 만들 유저 검색
		Users storeOwner = findStoreOwnerByUsername(storeCreateRequestDto.getStoreOwnerName());
		// 식당 생성 및 저장
		Stores store = storeCreateRequestDto.toEntity(storeOwner);
		Stores newStore = storeRepository.save(store);
		// 식당 반환
		return StoreResponseDto.fromEntity(newStore);
	}

	/**
	 * 식당 전체 조회
	 * @param userId : 조회할 사용자의 ID
	 * @param searchDto : 조회할 내용
	 * @return : 조회한 식당의 정보들을 반환
	 */
	public PagedModel<StoreListResponseDto> getStores(UUID userId, StoreSearchDto searchDto) {
		try {
			log.info("Service로 getStores 요청 넘어옴");
			Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(),
				searchDto.getDirection(), searchDto.getSortBy());

			Role role = Role.CUSTOMER;
			if (userId != null) {
				role = findUserRole(userId);
			}

			log.info("role: " + role.toString());

			Page<StoreListResponseDto> storePage = storeRepository.searchStores(searchDto, pageable, role);
			return new PagedModel<>(storePage);
		} catch (Exception e) {
			log.error("상태 코드 : " + Constant.Code.STORE_ERROR + " / 메시지 : " + e.getMessage());
			throw new BaseException("식당 리스트를 가져올 수 없습니다.", Constant.Code.STORE_ERROR, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 식당 단건 조회
	 * @param storeId : 조회할 식당의 ID
	 * @return : 조회한 식당의 정보
	 */
	@Transactional(readOnly = true)
	public StoreResponseDto getOneStore(UUID storeId) {
		Stores store = findStore(storeId);

		long storeReviewCnt = reviewRepository.getCntReviews(store.getId());
		double storeRating = reviewRepository.getAvgReviewRatings(store.getId());

		return StoreResponseDto.fromEntityReview(store, storeReviewCnt, storeRating);
	}

	/**
	 * 식당 수정
	 * @param storeId : 수정할 식당의 ID
	 * @param userId : 식당 내용을 수정하려는 사용자의 ID
	 * @param storeRequestDto : 수정하려는 식당 내용
	 * @return : 수정을 완료한 식당 정보
	 */
	@Transactional
	public StoreResponseDto updateStore(UUID storeId, UUID userId, StoreUpdateRequestDto storeRequestDto) {
		// 유저의 권한이 OWNER 라면, store에 저장된 유저와 일치하는지 확인해야 함
		Users user = findStoreOwner(userId);
		Stores store = findStore(storeId);

		if (user.getRole() == Role.OWNER &&
			!user.getUserId().equals(store.getUserId().getUserId())) {
			log.info(user.getUserId().toString());
			throw new BaseException("해당 식당의 주인이 아닙니다.", Constant.Code.STORE_ERROR, HttpStatus.FORBIDDEN);
		}

		// 식당 내용 update
		Stores updateStore = storeRequestDto.toEntity();
		store.updateStore(updateStore);

		long storeReviewCnt = reviewRepository.getCntReviews(store.getId());
		double storeRating = reviewRepository.getAvgReviewRatings(store.getId());

		return StoreResponseDto.fromEntityReview(store, storeReviewCnt, storeRating);
	}

	/**
	 * 식당 삭제
	 * @param storeId : 삭제할 식당의 ID
	 * @param userId : 식당 내용을 삭제하려는 사용자의 ID
	 * @return : 삭제 상황
	 */
	@Transactional
	public ApiResponse deleteStore(UUID storeId, UUID userId) {
		// 유저의 권한이 OWNER 라면, store에 저장된 유저와 일치하는지 확인해야 함
		Users user = findStoreOwner(userId);
		Stores store = findStore(storeId);

		if (user.getRole() == Role.OWNER &&
			!user.getUserId().equals(store.getUserId().getUserId())) {
			throw new BaseException("해당 식당의 주인이 아닙니다.", Constant.Code.STORE_ERROR, HttpStatus.FORBIDDEN);
		}

		// 식당 삭제
		store.delete(true, user.getUserId().toString());
		storeRepository.save(store);
		log.info("삭제 여부 : " + store.getIsDeleted());
		return ApiResponse.ok("삭제 성공", "식당명 : " + store.getStoreName());
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
	 * 식당 주인 탐색
	 * @param userId : 식당의 주인 ID
	 * @return : 조회한 사용자
	 */
	public Users findStoreOwner(UUID userId) {
		return userRepository.findById(userId).filter(s -> !s.getIsDeleted()).orElseThrow(
			() -> new BaseException("존재하지 않는 유저", -1, HttpStatus.NOT_FOUND));
	}

	/**
	 * 사용자명으로 식당 주인 탐색
	 * @param username : 식당을 차릴 사용자명
	 * @return : 조회한 사용자
	 */
	public Users findStoreOwnerByUsername(String username) {
		return userRepository.findByUsername(username).filter(s -> !s.getIsDeleted()).orElseThrow(
			() -> new BaseException("존재하지 않는 유저", -1, HttpStatus.NOT_FOUND));
	}


	/**
	 * 사용자 권한 탐색
	 * @param userId : 권한을 보려는 사용자명
	 * @return : 조회한 사용자의 권한
	 */
	public Role findUserRole(UUID userId) {
		return userRepository.findById(userId).filter(s -> !s.getIsDeleted()).orElseThrow(
			() -> new BaseException("존재하지 않는 유저", -1, HttpStatus.NOT_FOUND)).getRole();
	}
}

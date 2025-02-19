package com.sparta.first.project.eighteen.domain.stores;

import java.util.UUID;

import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.common.security.UserDetailsImpl;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreListResponseDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreRequestDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreResponseDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreSearchDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreUpdateRequestDto;
import com.sparta.first.project.eighteen.model.users.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	/**
	 * 식당 생성
	 * @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @param storeRequestDto : 식당 정보
	 * @return : 생성한 식당의 내용
	 */
	@PostMapping
	public ResponseEntity<ApiResponse<StoreResponseDto>> createStore (
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody StoreRequestDto storeRequestDto) {

		// userDetails 담긴 유저는 MASTER 이어야만 함
		// MASTER 만 가게 생성 가능 (가게 생성 시 OWNER를 넣어주는 방식으로 진행)
		if (storeService.findUserRole(userDetails.getUsername()) != Role.MASTER) {
			log.info("MASTER가 아닌 사용자");
			throw new IllegalArgumentException("해당 사용자는 식당을 생성할 수 없습니다.");
		}

		StoreResponseDto responseDto = storeService.createStore(storeRequestDto);
		return ResponseEntity.ok(ApiResponse.ok("성공", responseDto));
	}

	/**
	 * 식당 목록 조회 (+서칭 기능)
	 * @return : 식당 목록 반환
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<PagedModel<StoreListResponseDto>>> getStores(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@ModelAttribute StoreSearchDto searchDto){
		log.info("getStores 요청 들어옴");
		PagedModel<StoreListResponseDto> responseDtos = storeService.getStores(userDetails.getUsername(), searchDto);
		return ResponseEntity.ok(ApiResponse.ok("성공", responseDtos));
	}

	/**
	 * 식당 단건 조회
	 * @param storeId : 조회할 식당의 ID
	 * @return : 조회한 식당의 내용
	 */
	@GetMapping("/{storeId}")
	public ResponseEntity<ApiResponse<StoreResponseDto>> getOneStore(@PathVariable UUID storeId){
		log.info("storeId: " + storeId.toString());
		StoreResponseDto responseDto = storeService.getOneStore(storeId);
		return ResponseEntity.ok(ApiResponse.ok("성공", responseDto));
	}

	/**
	 * 식당 수정
	 * @param storeId : 수정할 식당의 ID
	 * @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @param storeRequestDto : 수정할 식당 내용
	 * @return : 수정한 식당 내용
	 */
	@PutMapping("/{storeId}")
	public ResponseEntity<ApiResponse<StoreResponseDto>> updateStore (
		@PathVariable UUID storeId,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody StoreUpdateRequestDto storeRequestDto) {

		if (storeService.findUserRole(userDetails.getUsername()) == Role.CUSTOMER) {
			log.info("CUSTOMER인 사용자가 수정하려고 함");
			throw new IllegalArgumentException("고객은 식당의 정보를 수정할 수 없습니다.");
		}

		log.info("storeId: " + storeId);
		StoreResponseDto responseDto = storeService.updateStore(storeId, userDetails.getUsername(), storeRequestDto);
		return ResponseEntity.ok(ApiResponse.ok("성공", responseDto));
	}

	/**
	 * 식당 삭제
	 * @param storeId : 삭제할 식당의 ID
	 * @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @return : 상태코드 및 메시지 반환
	 */
	@DeleteMapping("/{storeId}")
	public ResponseEntity<ApiResponse> deleteStore (
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID storeId){

		if (storeService.findUserRole(userDetails.getUsername()) == Role.CUSTOMER) {
			log.info("CUSTOMER인 사용자가 삭제하려고 함");
			throw new IllegalArgumentException("고객은 식당을 삭제할 수 없습니다.");
		}

		log.info("storeId: " + storeId);
		ApiResponse apiResponse = storeService.deleteStore(storeId, userDetails.getUsername());
		return ResponseEntity.ok(apiResponse);
	}

}

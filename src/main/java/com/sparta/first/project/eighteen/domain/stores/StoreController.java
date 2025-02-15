package com.sparta.first.project.eighteen.domain.stores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreListResponseDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreRequestDto;
import com.sparta.first.project.eighteen.domain.stores.dtos.StoreResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

	/**
	 * 식당 생성
	 // * @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @param storeRequestDto : 식당 정보
	 * @return : 생성한 식당의 내용
	 */
	@PostMapping
	public ResponseEntity<ApiResponse<StoreResponseDto>> createStore (
		// @AuthenticationPrincipal UserDetails userDetails,
		@RequestBody StoreRequestDto storeRequestDto) {

		// 권한 확인하기
		// userDetails.getAuthorities();
		return ResponseEntity.ok(ApiResponse.ok("성공", new StoreResponseDto(storeRequestDto)));
	}

	/**
	 * 식당 목록 조회
	 * @return : 식당 목록 반환 (Searching 기능 제공)
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<Page<StoreListResponseDto>>> getStores(Pageable pageable){
		return ResponseEntity.ok(ApiResponse.ok("성공", null));
	}

	/**
	 * 식당 단건 조회
	 * @param storeId : 조회할 식당의 ID
	 * @return : 조회한 식당의 내용
	 */
	@GetMapping("/{storeId}")
	public ResponseEntity<ApiResponse<StoreResponseDto>> getOneStore(@PathVariable String storeId){
		return ResponseEntity.ok(ApiResponse.ok("성공", new StoreResponseDto()));
	}

	/**
	 * 식당 수정
	 * @param storeId : 수정할 식당의 ID
	 // * @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @param storeRequestDto : 수정할 식당 내용
	 * @return : 수정한 식당 내용
	 */
	@PutMapping("/{storeId}")
	public ResponseEntity<ApiResponse<StoreResponseDto>> updateStore (
		@PathVariable String storeId,
		// @AuthenticationPrincipal UserDetails userDetails,
		@RequestBody StoreRequestDto storeRequestDto) {

		// 권한 확인하기
		// userDetails.getAuthorities();
		log.info("storeId: " + storeId);
		return ResponseEntity.ok(ApiResponse.ok("성공", new StoreResponseDto(storeRequestDto)));
	}

	/**
	 * 식당 삭제
	 * @param storeId : 삭제할 식당의 ID
	 // * @param userDetails : 현재 로그인한 사용자 (권한 확인)
	 * @return : 상태코드 및 메시지 반환
	 */
	@DeleteMapping("/{storeId}")
	public ResponseEntity<ApiResponse> deleteStore (
		// @AuthenticationPrincipal UserDetails userDetails,
		@PathVariable String storeId){

		// 권한 확인하기
		// userDetails.getAuthorities();
		log.info("storeId: " + storeId);
		return ResponseEntity.ok(ApiResponse.ok("성공", null));
	}

}

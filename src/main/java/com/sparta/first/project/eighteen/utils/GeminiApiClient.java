package com.sparta.first.project.eighteen.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GeminiApiClient {

	@Value("${gemini.api-key}")
	private String apiKey;

	@Value("${gemini.api-url}")
	private String apiUrl;

	private RestTemplate restTemplate = new RestTemplate();

	public String GeminiResponse(String question) {
		// 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 요청 JSON 구조 생성
		Map<String, Object> textPart = Collections.singletonMap("text", question);
		Map<String, Object> parts = Collections.singletonMap("parts", Collections.singletonList(textPart));
		Map<String, Object> contents = Collections.singletonMap("contents", Collections.singletonList(parts));

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(contents, headers);
		ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

		// 응답 데이터 파싱
		Map<String, Object> responseBody = response.getBody();
		if (responseBody != null && responseBody.containsKey("candidates")) {
			List<Map<String, Object>> candidates = (List<Map<String, Object>>)responseBody.get("candidates");

			if (!candidates.isEmpty()) {
				Map<String, Object> content = (Map<String, Object>)candidates.get(0).get("content");
				if (content != null && content.containsKey("parts")) {
					List<Map<String, Object>> partsList = (List<Map<String, Object>>)content.get("parts");
					if (!partsList.isEmpty() && partsList.get(0).containsKey("text")) {
						return (String)partsList.get(0).get("text");
					}
				}
			}
		}

		return "응답을 가져올 수 없습니다.";
	}

}

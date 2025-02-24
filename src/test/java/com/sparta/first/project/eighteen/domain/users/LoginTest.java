package com.sparta.first.project.eighteen.domain.users;

import static org.mockito.BDDMockito.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.common.security.jwt.JwtUtil;
import com.sparta.first.project.eighteen.domain.users.dtos.LoginRequestDto;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class LoginTest {
	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	JwtUtil jwtUtil;

	ObjectMapper objectMapper = new ObjectMapper();

	@ParameterizedTest
	@DisplayName("로그인 성공 테스트")
	@CsvSource(value = {
		"customer:customer:eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmY2NmMzQ0OC0wM2M3LTQ3YTQtYTEwOC1jZTZjMzk4MTVhMzciLCJhdXRoIjoiUk9MRV9DVVNUT01FUiIsImlzcyI6ImVpZ2h0ZWVuIiwiaWF0IjoxNzQwMjAzMTg2LCJleHAiOjE3NDE0MTI3ODZ9.vjC1_y3msUKVlaZqQhAkHL7LwoVFvSJ6mkt2jDn-OnY5J-eH2Rrp1lkqNGWe2SH275AQUwUQFzqOD-g0C6XOGw",
		"owner:owner:eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzYTUwM2NkOS04M2IyLTQzNzQtOTQwNS1lMmRlZThjNTZiMWMiLCJhdXRoIjoiUk9MRV9PV05FUiIsImlzcyI6ImVpZ2h0ZWVuIiwiaWF0IjoxNzQwMjAzMjQ3LCJleHAiOjE3NDE0MTI4NDd9.HAadDH7zBcWmjSQm9E4M4GPjRHMeB52Wcn5bJt04cM9cIWDsGrdHJ2VI25hFEj4WMv3KuqiVW_OCMjUul7BQtw",
		"manager:manager:eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYWUwNzZlMy01ODQyLTQyMWYtYTExOS1iNjQ2NjYwM2I5NjQiLCJhdXRoIjoiUk9MRV9NQU5BR0VSIiwiaXNzIjoiZWlnaHRlZW4iLCJpYXQiOjE3NDAyMDMyODEsImV4cCI6MTc0MTQxMjg4MX0.e3qGQH0qM8RX2rSzo3oF-5PauvQq1XV9kHKLxgSDl5e1GURIn3a8_Mww7Y5HRcYZlBxq3I-ilglyt7DMBLbdlg"
	}, delimiter = ':')
	public void loginSuccessTest(String username, String password, String accToken) throws Exception {
		// given
		LoginRequestDto requestDto = new LoginRequestDto(username, password);
		ApiResponse<Map<String, String>> apiResponse = ApiResponse.ok("로그인 성공", Map.of("token", accToken));
		given(jwtUtil.generateAccessToken(anyString(), anyString())).willReturn(accToken);

		// when
		ResultActions perform = mockMvc.perform(
			MockMvcRequestBuilders.post("/api/v1/auth/sign-in")
				.content(objectMapper.writeValueAsString(requestDto))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		perform.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiResponse)))
			.andReturn();
	}
}

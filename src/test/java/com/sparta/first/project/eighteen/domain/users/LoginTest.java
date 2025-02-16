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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.first.project.eighteen.common.dto.ApiResponse;
import com.sparta.first.project.eighteen.common.security.jwt.JwtUtil;
import com.sparta.first.project.eighteen.domain.users.dtos.LoginRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	JwtUtil jwtUtil;

	ObjectMapper objectMapper = new ObjectMapper();

	@ParameterizedTest
	@DisplayName("로그인 성공 테스트")
	@CsvSource(value = {
		"owner:owner:eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvd25lciIsImF1dGgiOiJST0xFX09XTkVSIiwiaXNzIjoiZWlnaHRlZW4iLCJpYXQiOjE3NDA5MTAwMTR9.4nxOJmZjF-074SeufD6VGQCeXfrZ-x55lsyEAX7VgdmkpWj0E3W__6rwPW8ex5W2xdF85hG00H7rVDSwZaIf-Q",
		"customer:customer:eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjdXN0b21lciIsImF1dGgiOiJST0xFX0NVU1RPTUVSIiwiaXNzIjoiZWlnaHRlZW4iLCJpYXQiOjE3NDA5MTAwNDR9.sPsfOyKfz2NkHhXR8y9dkpJnl0DVyMgUmIHKkGNOgw9wEyQe0WsYiopHFmrtayhhNSahKG-uzrq-p_cC69-mIg"
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

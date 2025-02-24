package com.sparta.first.project.eighteen.domain.users;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.first.project.eighteen.domain.users.dtos.LoginRequestDto;
import com.sparta.first.project.eighteen.domain.users.dtos.UserUpdateRequestDto;
import com.sparta.first.project.eighteen.model.users.Users;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DeletedUserTest {

	@Autowired
	MockMvc mvc;

	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@MockitoBean
	UserRepository userRepository;

	@Autowired
	private UserService userService;

	private ObjectMapper objectMapper = new ObjectMapper();

	UUID userId = UUID.fromString("fccf3448-03c7-47a4-a108-ce6c39815a37");
	Users customer = Users.builder()
		.userId(userId)
		.username("customer")
		.userPassword(passwordEncoder.encode("customer"))
		// .role(Role.CUSTOMER)
		.isDeleted(true)
		.build();

	@BeforeAll
	void init() {
		BDDMockito.given(userRepository.findById(BDDMockito.any(UUID.class))).willReturn(Optional.of(customer));
		// 시작 시 유저 삭제 처리
		userService.deleteUser(userId);
	}

	/**
	 * 삭제 이전에 발급받은 토큰으로 로그인 시도 시 차단
	 */
	@Test
	@DisplayName("삭제 회원 - 로그인")
	void deleteUserLoginTest() throws Exception {
		// given
		LoginRequestDto requestDto = new LoginRequestDto("customer", "customer");
		BDDMockito.given(userRepository.findByUsername(BDDMockito.anyString())).willReturn(Optional.of(customer));

		// when
		ResultActions perform = mvc.perform(
			MockMvcRequestBuilders.post("/api/v1/auth/sign-in")
				.content(objectMapper.writeValueAsString(requestDto))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		perform.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());

	}

	@Test
	@DisplayName("삭제 회원 - 회원 조회")
	void deletedUserInfoTest() throws Exception {
		// given
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmY2NmMzQ0OC0wM2M3LTQ3YTQtYTEwOC1jZTZjMzk4MTVhMzciLCJhdXRoIjoiUk9MRV9DVVNUT01FUiIsImlzcyI6ImVpZ2h0ZWVuIiwiaWF0IjoxNzQwMjAzMTg2LCJleHAiOjE3NDE0MTI3ODZ9.vjC1_y3msUKVlaZqQhAkHL7LwoVFvSJ6mkt2jDn-OnY5J-eH2Rrp1lkqNGWe2SH275AQUwUQFzqOD-g0C6XOGw";

		// when
		ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/admin/v1/users")
			.header("Authorization", "Bearer " + token)
		);

		// then
		perform.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("삭제 회원 - 회원 수정")
	void deletedUserModifyTest() throws Exception {
		// given
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmY2NmMzQ0OC0wM2M3LTQ3YTQtYTEwOC1jZTZjMzk4MTVhMzciLCJhdXRoIjoiUk9MRV9DVVNUT01FUiIsImlzcyI6ImVpZ2h0ZWVuIiwiaWF0IjoxNzQwMjAzMTg2LCJleHAiOjE3NDE0MTI3ODZ9.vjC1_y3msUKVlaZqQhAkHL7LwoVFvSJ6mkt2jDn-OnY5J-eH2Rrp1lkqNGWe2SH275AQUwUQFzqOD-g0C6XOGw";
		UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
			.email("test123@test.com")
			.build();
		// when
		ResultActions perform = mvc.perform(MockMvcRequestBuilders.put("/api/admin/v1/users")
			.content(objectMapper.writeValueAsString(requestDto)).contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + token)
		);

		// then
		perform.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("삭제 회원 - 회원 삭제")
	void deletedUserDeleteTest() throws Exception {
		// given
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmY2NmMzQ0OC0wM2M3LTQ3YTQtYTEwOC1jZTZjMzk4MTVhMzciLCJhdXRoIjoiUk9MRV9DVVNUT01FUiIsImlzcyI6ImVpZ2h0ZWVuIiwiaWF0IjoxNzQwMjAzMTg2LCJleHAiOjE3NDE0MTI3ODZ9.vjC1_y3msUKVlaZqQhAkHL7LwoVFvSJ6mkt2jDn-OnY5J-eH2Rrp1lkqNGWe2SH275AQUwUQFzqOD-g0C6XOGw";

		// when
		ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete("/api/admin/v1/users")
			.header("Authorization", "Bearer " + token)
		);

		// then
		perform.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

}

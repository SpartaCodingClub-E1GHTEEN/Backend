package com.sparta.first.project.eighteen.domain.users;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.first.project.eighteen.domain.users.dtos.UserRequestDto;
import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.Users;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDataInitializer {

	private final UserRepository userRepository;

	@PostConstruct
	@Transactional
	public void init() {
		UUID customerUUID = UUID.fromString("fccf3448-03c7-47a4-a108-ce6c39815a37");
		UUID ownerUUID = UUID.fromString("3a503cd9-83b2-4374-9405-e2dee8c56b1c");
		UUID adminUUID = UUID.fromString("fae076e3-5842-421f-a119-b6466603b964");

		Users customer = UserRequestDto.builder()
			.username("customer")
			.password("{bcrypt}$2a$12$v.pBIbASHb6ICXgtSrxXM.wHXfJvb4Hzlo.JKxNfAZgzhPXOTnNay")
			.nickname("고객")
			.phone("011-1111-1111")
			.email("test@test.io")
			.address("서울시 광화문구")
			.role(Role.CUSTOMER)
			.build()
			.toEntityWithUUID(customerUUID);

		Users owner = UserRequestDto.builder()
			.username("owner")
			.password("{bcrypt}$2a$12$iA4FFaXScbLmebWSEadD0.0LqyaW1aBrQ/Rp2GGa/Dj9QmxQZN/Xe")
			.nickname("가게주인")
			.phone("010-0000-0000")
			.email("test1@test.io")
			.address("서울시 강서구")
			.role(Role.OWNER)
			.build()
			.toEntityWithUUID(ownerUUID);

		Users admin = UserRequestDto.builder()
			.username("manager")
			.password("{bcrypt}$2a$12$SGiz3sKC1yz/dtwpGlgPO.ND8t2tF4PUDFKx4cZw9tmOw6U/U46Ha")
			.nickname("관리자")
			.phone("011-2222-2222")
			.email("test2@test.io")
			.address("인천시 부평구")
			.role(Role.MANAGER)
			.build()
			.toEntityWithUUID(adminUUID);

		userRepository.saveAll(List.of(customer, owner, admin));
	}
}

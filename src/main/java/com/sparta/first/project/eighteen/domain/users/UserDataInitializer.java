// package com.sparta.first.project.eighteen.domain.users;
//
// import java.util.List;
// import java.util.UUID;
//
// import org.springframework.stereotype.Component;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.sparta.first.project.eighteen.model.users.Role;
// import com.sparta.first.project.eighteen.model.users.Users;
//
// import jakarta.annotation.PostConstruct;
// import lombok.RequiredArgsConstructor;
//
// @Component
// @RequiredArgsConstructor
// public class UserDataInitializer {
//
// 	private final UserRepository userRepository;
//
// 	@PostConstruct
// 	@Transactional
// 	public void init() {
// 		UUID customerUUID = UUID.fromString("fccf3448-03c7-47a4-a108-ce6c39815a37");
// 		UUID ownerUUID = UUID.fromString("3a503cd9-83b2-4374-9405-e2dee8c56b1c");
// 		UUID adminUUID = UUID.fromString("fae076e3-5842-421f-a119-b6466603b964");
//
// 		Users customer = Users.builder()
// 			// .userId(customerUUID)
// 			.userPassword("{bcrypt}$2a$12$v.pBIbASHb6ICXgtSrxXM.wHXfJvb4Hzlo.JKxNfAZgzhPXOTnNay")
// 			.username("customer")
// 			.userPhone("011-1111-1111")
// 			.userAddress("서울시 광화문구")
// 			.role(Role.CUSTOMER)
// 			.build();
//
// 		Users owner = Users.builder()
// 			// .userId(ownerUUID)
// 			.username("owner")
// 			.userPassword("{bcrypt}$2a$12$iA4FFaXScbLmebWSEadD0.0LqyaW1aBrQ/Rp2GGa/Dj9QmxQZN/Xe")
// 			.userPhone("010-0000-0000")
// 			.userAddress("서울시 강서구")
// 			.role(Role.OWNER)
// 			.build();
//
// 		Users admin = Users.builder()
// 			// .userId(adminUUID)
// 			.username("admin")
// 			.userPassword("{bcrypt}$2a$12$Rv3hnHwiDWX2/dzKKx5Cku1kZ0stRR9tMoakNcm7Lcgp3Yp7kYCde")
// 			.userPhone("010-2222-2222")
// 			.userAddress("인천시 부평구")
// 			.role(Role.MANAGER)
// 			.build();
//
// 		userRepository.saveAll(List.of(customer, owner, admin));
// 	}
// }

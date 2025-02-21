package com.sparta.first.project.eighteen.domain.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest({UserController.class})
public class DeletedUserTest {

	@Autowired
	WebApplicationContext ac;

	MockMvc mvc;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@BeforeEach
	void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(ac)
			.build();
	}

}

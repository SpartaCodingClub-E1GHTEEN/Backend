package com.sparta.first.project.eighteen.domain.users;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
	"com.sparta.first.project.eighteen.domain.users",
	"com.sparta.first.project.eighteen.common.security",
	"com.sparta.first.project.eighteen.config"
})
@EnableJpaRepositories("com.sparta.first.project.eighteen.domain.users")
@EntityScan("com.sparta.first.project.eighteen.model.users")
public class UsersIntegrationApp {
}

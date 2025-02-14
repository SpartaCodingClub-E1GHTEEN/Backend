package com.sparta.first.project.eighteen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EighteenApplication {

	public static void main(String[] args) {
		SpringApplication.run(EighteenApplication.class, args);
	}

}

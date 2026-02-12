package com.hellFire.QuizGame;

import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class QuizGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizGameApplication.class, args);
	}

}

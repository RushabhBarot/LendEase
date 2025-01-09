package com.example.LendEase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LendEaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(LendEaseApplication.class, args);
	}

}

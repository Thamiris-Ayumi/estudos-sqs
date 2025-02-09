package com.thamiris.api_banco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiBancoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBancoApplication.class, args);
	}
}

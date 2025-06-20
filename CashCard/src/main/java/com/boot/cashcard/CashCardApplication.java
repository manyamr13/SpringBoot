package com.boot.cashcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication

@EnableFeignClients(basePackages = "com.boot.cashcard.fiegnclient")
public class CashCardApplication {
	public static void main(String[] args) {

		SpringApplication.run(CashCardApplication.class, args);
	}
}

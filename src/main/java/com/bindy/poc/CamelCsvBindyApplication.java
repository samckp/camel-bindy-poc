package com.bindy.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.bindy.poc")
public class CamelCsvBindyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelCsvBindyApplication.class, args);
	}
}

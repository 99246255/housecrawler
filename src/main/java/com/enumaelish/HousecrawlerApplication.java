package com.enumaelish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HousecrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HousecrawlerApplication.class, args);
	}
}

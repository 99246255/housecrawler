package com.enumaelish;

import com.enumaelish.config.ApplicationReadyListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HousecrawlerApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(HousecrawlerApplication.class);
		springApplication.addListeners(new ApplicationReadyListener());
		springApplication.run(args);
	}
}

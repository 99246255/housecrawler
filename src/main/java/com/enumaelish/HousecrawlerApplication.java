package com.enumaelish;

import com.enumaelish.config.LianjiaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HousecrawlerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(HousecrawlerApplication.class, args);
		LianjiaConfig bean = run.getBean(LianjiaConfig.class);
		bean.init();
	}


}

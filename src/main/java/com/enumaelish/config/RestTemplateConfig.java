package com.enumaelish.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * RestTemplate配置类
 */
@Configuration
public class RestTemplateConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000*1000);
		requestFactory.setReadTimeout(1000*1000);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTemplate;
	}

}

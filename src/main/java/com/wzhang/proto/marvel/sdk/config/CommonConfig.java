package com.wzhang.proto.marvel.sdk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * misc configuration
 */
@Configuration
public class CommonConfig {
	/**
	 * singleton of rest template instance
	 * 
	 * @return
	 */
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

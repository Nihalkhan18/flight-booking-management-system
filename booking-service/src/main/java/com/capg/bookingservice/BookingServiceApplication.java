package com.capg.bookingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EntityScan(basePackages = "com.capg.entity")
@ComponentScan(basePackages = "com.capg")
@ComponentScan(basePackages = "com.capg.controller")
@ComponentScan(basePackages = "com.capg.service")
@ComponentScan(basePackages = "com.capg.dto")
@EnableJpaRepositories("com.capg.repository")

public class BookingServiceApplication {

	@Bean
	public RestTemplate getRestTemplate() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(3000);
		return new RestTemplate(clientHttpRequestFactory);
	}
	public static void main(String[] args) {
		SpringApplication.run(BookingServiceApplication.class, args);
	}

}

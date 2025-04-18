package com.capg.searchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = "com.capg.entity")
@ComponentScan(basePackages = "com.capg.controller")
@ComponentScan(basePackages = "com.capg.service")
@ComponentScan(basePackages = "com.capg.dto")
@EnableJpaRepositories(basePackages = "com.capg.repository") 
//@EnableEurekaClient
public class SearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
	}

}

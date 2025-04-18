package com.capg.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.capg.entity")  // Explicitly set the base package for entities
@ComponentScan(basePackages = "com.capg.controller")
@ComponentScan(basePackages = "com.capg.service")
@ComponentScan(basePackages = "com.capg.dto")
@ComponentScan(basePackages = "com.capg.security")
@EnableJpaRepositories("com.capg.repository")
//@EnableEurekaClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}

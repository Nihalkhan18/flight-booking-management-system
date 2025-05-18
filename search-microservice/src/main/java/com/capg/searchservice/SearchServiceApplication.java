package com.capg.searchservice;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.capg.entity.Flights;
import com.capg.entity.Seat;
import com.capg.repository.FlightRepository;
import com.capg.repository.SeatRepository;


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

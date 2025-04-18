package com.capg.service;

import com.capg.entity.Flights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class FlightsInfo {

    @Autowired
    private RestTemplate restTemplate;

    // Using Hystrix for fault tolerance, can be uncommented if needed.
//     @HystrixCommand(fallbackMethod = "getFallbackFlightDetails")
    public Flights getFlightDetails(int flightId) {
        try {
            return restTemplate.getForObject("http://search-microservice/flights/get/" + flightId, Flights.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Log the error and return a fallback value or handle it accordingly
            System.out.println("Error occurred while fetching flight details: " + e.getMessage());
            return getFallbackFlightDetails(flightId); // Fallback method can be used if the service is down.
        } catch (Exception e) {
            // Handle other unexpected exceptions
            System.out.println("Unexpected error occurred: " + e.getMessage());
            return getFallbackFlightDetails(flightId); // Return fallback in case of an unexpected exception
        }
    }

    // Fallback method in case of failure
    public Flights getFallbackFlightDetails(int flightId) {
        // You can return some default values or null based on the application logic
        System.out.println("Returning fallback flight details for flightId: " + flightId);
        Flights fallbackFlight = new Flights();
        fallbackFlight.setFlightId(flightId);
        fallbackFlight.setFlightName("Fallback Flight");
        fallbackFlight.setAvailableSeats(0); // Set default values
        return fallbackFlight;
    }
}

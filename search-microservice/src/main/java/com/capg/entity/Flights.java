package com.capg.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.capg.dto.FlightsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightId;

    private String flightName;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private Integer seats;
    private Integer fare;

    private LocalDate travelDate;

    @Column(name = "available_male_seats")
    private Integer availableMaleSeats;

    @Column(name = "available_female_seats")
    private Integer availableFemaleSeats;

    // DTO Constructor
    public Flights(FlightsDTO flightsDTO) {
        this.flightId = flightsDTO.getFlightId();
        this.flightName = flightsDTO.getFlightName();
        this.origin = flightsDTO.getOrigin();
        this.destination = flightsDTO.getDestination();
        this.departureTime = flightsDTO.getDepartureTime();
        this.arrivalTime = flightsDTO.getArrivalTime();
        this.seats = flightsDTO.getSeats();
        this.fare = flightsDTO.getFare();
        this.travelDate = flightsDTO.getTravelDate();
        this.availableMaleSeats = flightsDTO.getAvailableMaleSeats();
        this.availableFemaleSeats = flightsDTO.getAvailableFemaleSeats();
    }
}

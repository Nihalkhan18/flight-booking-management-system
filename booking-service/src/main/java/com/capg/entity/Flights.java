package com.capg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private int flightId;

    @Column(name = "flight_name")
    private String flightName;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "departure_time")
    private String departureTime;

    @Column(name = "arrival_time")
    private String arrivalTime;

    @Column(name = "seats")
    private Integer seats;

    @Column(name = "fare")
    private Integer fare;

    @Column(name = "travel_date")
    private LocalDate travelDate;

    @Column(name = "available_male_seats")
    private Integer availableMaleSeats;

    @Column(name = "available_female_seats")
    private Integer availableFemaleSeats;

    // One flight can have many bookings (One-to-Many relationship)
    @OneToMany(mappedBy = "flights", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingDetails> bookingDetails;
}

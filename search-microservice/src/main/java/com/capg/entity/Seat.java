package com.capg.entity;

import javax.persistence.*;

import lombok.Data;
@Data
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied;

    @Column(name = "gender")
    private String gender; // "MALE", "FEMALE", or null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flights flight;

    // Getters and Setters
    // ...
}

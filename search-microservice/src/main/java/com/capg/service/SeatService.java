package com.capg.service;

import com.capg.entity.Seat;
import com.capg.entity.Flights;
import com.capg.repository.SeatRepository;
import com.capg.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FlightRepository flightRepository;

    public List<Seat> getSeatsByFlightId(int flightId) {
        Flights flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found"));
        return seatRepository.findByFlight(flight);
    }

    public Seat updateSeatStatus(Long seatId, boolean isOccupied, String gender) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("Seat not found"));
        seat.setOccupied(isOccupied);
        seat.setGender(gender);
        return seatRepository.save(seat);
    }

    // Additional methods as needed
}

package com.capg.controller;

import com.capg.entity.Seat;
import com.capg.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/seats")
@CrossOrigin(origins = "http://localhost:3000")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/flight/{flightId}")
    public List<Seat> getSeatsByFlight(@PathVariable int flightId) {
        return seatService.getSeatsByFlightId(flightId);
    }

    @PutMapping("/{seatId}")
    public Seat updateSeat(@PathVariable Long seatId, @RequestBody Seat seatDetails) {
        return seatService.updateSeatStatus(seatId, seatDetails.isOccupied(), seatDetails.getGender());
    }

    // Additional endpoints as needed
}

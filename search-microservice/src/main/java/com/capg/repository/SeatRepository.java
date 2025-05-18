package com.capg.repository;

import com.capg.entity.Seat;
import com.capg.entity.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlight(Flights flight);
}

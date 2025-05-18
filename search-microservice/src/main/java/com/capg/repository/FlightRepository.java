package com.capg.repository;

import com.capg.entity.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository("searchFlightRepository")
public interface FlightRepository extends JpaRepository<Flights, Integer> {

    // Custom query using JPQL
    @Query("SELECT f FROM Flights f WHERE f.origin = :origin AND f.destination = :destination")
    List<Flights> findByOriginAndDestination(@Param("origin") String origin, @Param("destination") String destination);

    List<Flights> findByOriginAndDestinationAndTravelDate(String origin, String destination, LocalDate travelDate);

}

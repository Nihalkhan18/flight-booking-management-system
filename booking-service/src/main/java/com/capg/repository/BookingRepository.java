package com.capg.repository;

import com.capg.dto.BookingDetailsDTO;
import com.capg.entity.BookingDetails;
import com.capg.entity.Flights;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bookingFlightRepository")
public interface BookingRepository extends JpaRepository<BookingDetails, Integer>{
    
	BookingDetails save(BookingDetails bookingDetails); // ✅ Already inherited from JpaRepository


	void save(List<Flights> asList);

	
}

package com.capg.repository;

import com.capg.dto.BookingDetailsDTO;
import com.capg.entity.BookingDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingDetails, Integer>{

	BookingDetailsDTO save(BookingDetailsDTO bookingDetailsDTO);
}

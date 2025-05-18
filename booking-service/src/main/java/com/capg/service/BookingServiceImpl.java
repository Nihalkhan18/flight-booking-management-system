package com.capg.service;

import com.capg.dto.BookingDetailsDTO;
import com.capg.dto.FlightsDTO;
import com.capg.entity.BookingDetails;
import com.capg.entity.Flights;
import com.capg.exception.BookingIdNotFoundException;
import com.capg.repository.BookingRepository;
import com.capg.repository.FlightRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class BookingServiceImpl implements BookingService{
	 @Autowired
	    private EntityManager entityManager;
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    @Qualifier("searchFlightRepository")
    private FlightRepository flightRepository;

//    @Autowired
//    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private FlightsInfo flightsInfo;

    //Get details of all booked flights
    @Override
    public List<BookingDetailsDTO> getBookingDetails() {
        List<BookingDetails> bookingDetails = bookingRepository.findAll();
        return bookingDetails.stream().map(BookingDetailsDTO::new).collect(Collectors.toList());
    }

    //Get booking details for given booking id
    @Override
    public BookingDetailsDTO getBookingDetailsById(Integer id) {
        BookingDetails bookingDetails = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking details do not exist for id: " + id));
        return new BookingDetailsDTO(bookingDetails);
    }

    //Book new flight
//    @Override
//    public BookingDetailsDTO newBooking(BookingDetailsDTO bookingDetailsDTO) {
//        // Check if the flightId exists in the flights table
//        String flightIdQuery = "SELECT COUNT(f) FROM Flight f WHERE f.flightId = :flightId";
//        Query query = entityManager.createQuery(flightIdQuery);
//        query.setParameter("flightId", bookingDetailsDTO.getFlightId());
//        Long count = (Long) query.getSingleResult();
//
//        if (count == 0) {
//            throw new IllegalArgumentException("Flight ID does not exist");
//        }
//
//        // Proceed to save the booking if the flight exists
//        return bookingRepository.save(bookingDetailsDTO);
//    }
    @Override
    public BookingDetailsDTO newBooking(BookingDetailsDTO dto) {
        // 1. Normalize gender input
        String gender = dto.getGender().trim().toLowerCase();
        int seatsToBook = dto.getRequiredSeats();

        // 2. Fetch the flight
        Flights flight = flightRepository.findById(dto.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + dto.getFlightId()));

        // 3. Deduct seats based on gender
        if (gender.equals("male")) {
            if (flight.getAvailableMaleSeats() < seatsToBook) {
                throw new RuntimeException("Not enough male seats available.");
            }
            flight.setAvailableMaleSeats(flight.getAvailableMaleSeats() - seatsToBook);
        } else if (gender.equals("female")) {
            if (flight.getAvailableFemaleSeats() < seatsToBook) {
                throw new RuntimeException("Not enough female seats available.");
            }
            flight.setAvailableFemaleSeats(flight.getAvailableFemaleSeats() - seatsToBook);
        } else {
            throw new RuntimeException("Invalid gender value. Please enter 'male' or 'female'.");
        }

        // 4. Save the updated flight
        flightRepository.save(flight);

        // 5. Create and save the booking
        BookingDetails booking = new BookingDetails(dto);
        booking.bookedTime();
        booking.updatedTime();

        BookingDetails saved = bookingRepository.save(booking);

        // 6. Return saved booking as DTO
        return new BookingDetailsDTO(saved);
    }



    //Update booking details
    @Override
    public BookingDetailsDTO updateBookingDetails(Integer id, BookingDetailsDTO bookingDetailsDTO) {
        BookingDetails bookingDetails = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking details do not exist for id: " + id));

        Optional<BookingDetails> optionalBookingDetails = bookingRepository.findById(id);
        bookingRepository.delete(bookingDetails);

        if (optionalBookingDetails.isPresent()) {
            BookingDetails bookingSave = optionalBookingDetails.get();

            bookingSave.setBookingId(bookingDetails.getBookingId());
            bookingSave.setFirstName(bookingDetailsDTO.getFirstName() != null ? bookingDetailsDTO.getFirstName() : bookingSave.getFirstName());
            bookingSave.setLastName(bookingDetailsDTO.getLastName() != null ? bookingDetailsDTO.getLastName() : bookingSave.getLastName());
            bookingSave.setGender(bookingDetailsDTO.getGender() != null ? bookingDetailsDTO.getGender() : bookingSave.getGender());
            bookingSave.setEmail(bookingDetailsDTO.getEmail() != null ? bookingDetailsDTO.getEmail() : bookingSave.getEmail());
            bookingSave.setPhoneNo(bookingDetailsDTO.getPhoneNo() != null ? bookingDetailsDTO.getPhoneNo() : bookingSave.getPhoneNo());
            bookingSave.setRequiredSeats(bookingDetailsDTO.getRequiredSeats() != null ? bookingDetailsDTO.getRequiredSeats() : bookingSave.getRequiredSeats());

            bookingSave.setFlightId(bookingDetailsDTO.getFlightId() != 0 ? bookingDetailsDTO.getFlightId() : bookingSave.getFlightId());
//            bookingSave.setFlights(bookingDetailsDTO.getFlights() != null ? bookingDetailsDTO.getFlights() : flightsInfo.getFlightDetails(bookingSave.getFlightId()));

            bookingSave.setBookedOn(bookingDetails.getBookedOn());
            bookingSave.updatedTime();
            bookingRepository.save(bookingSave);
            return new BookingDetailsDTO(bookingSave);
        }
        return new BookingDetailsDTO(bookingDetails);
    }
 // Search by origin, destination and date
    @Override
    public List<FlightsDTO> flightByOriginDestinationAndDate(String origin, String destination, LocalDate travelDate) {
        List<Flights> flights = flightRepository.findByOriginAndDestinationAndTravelDate(origin, destination, travelDate);
        return flights.stream().map(FlightsDTO::new).collect(Collectors.toList());
    }


    //Delete booking details for given id
    @Override
    public void deleteBookingDetailsById(Integer id) {
        BookingDetails bookingDetails = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking details do not exist for id: " + id));
        bookingRepository.delete(bookingDetails);
    }

    //Delete all booking data
    @Override
    public void deleteAll() {
        bookingRepository.deleteAll();
    }
}

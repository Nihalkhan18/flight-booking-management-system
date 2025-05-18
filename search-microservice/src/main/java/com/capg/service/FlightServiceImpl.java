package com.capg.service;

import com.capg.dto.FlightsDTO;
import com.capg.entity.Flights;
import com.capg.exception.FlightNotFoundException;
import com.capg.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    // Get all flights
    @Override
    public List<FlightsDTO> getFlights() {
        List<Flights> flights = flightRepository.findAll();
        return flights.stream().map(FlightsDTO::new).collect(Collectors.toList());
    }

    // Get flight by ID
    @Override
    public FlightsDTO getFlight(Integer id) {
        Flights flights = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight does not exist with id: " + id));
        return new FlightsDTO(flights);
    }

    // Search by origin and destination only
    @Override
    public List<FlightsDTO> flightByOriginAndDestination(String origin, String destination) {
        List<Flights> flights = flightRepository.findByOriginAndDestination(origin, destination);
        return flights.stream().map(FlightsDTO::new).collect(Collectors.toList());
    }

    // Search by origin, destination and date
    @Override
    public List<FlightsDTO> flightByOriginDestinationAndDate(String origin, String destination, LocalDate travelDate) {
        List<Flights> flights = flightRepository.findByOriginAndDestinationAndTravelDate(origin, destination, travelDate);
        return flights.stream().map(FlightsDTO::new).collect(Collectors.toList());
    }

    // Create new flight
    @Override
    public FlightsDTO newFlight(FlightsDTO flightsDTO) {
        Flights flights = new Flights(flightsDTO);
      
        return new FlightsDTO(flightRepository.save(flights));
    }

    // Update flight
    @Override
    public FlightsDTO updateFlight(Integer id, FlightsDTO flightsDTO) {
        Flights flights = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight does not exist with id: " + id));

        flights.setFlightName(flightsDTO.getFlightName() != null ? flightsDTO.getFlightName() : flights.getFlightName());
        flights.setOrigin(flightsDTO.getOrigin() != null ? flightsDTO.getOrigin() : flights.getOrigin());
        flights.setDestination(flightsDTO.getDestination() != null ? flightsDTO.getDestination() : flights.getDestination());
        flights.setDepartureTime(flightsDTO.getDepartureTime() != null ? flightsDTO.getDepartureTime() : flights.getDepartureTime());
        flights.setArrivalTime(flightsDTO.getArrivalTime() != null ? flightsDTO.getArrivalTime() : flights.getArrivalTime());
        flights.setSeats(flightsDTO.getSeats() != null ? flightsDTO.getSeats() : flights.getSeats());
        flights.setFare(flightsDTO.getFare() != null ? flightsDTO.getFare() : flights.getFare());
        flights.setTravelDate(flightsDTO.getTravelDate() != null ? flightsDTO.getTravelDate() : flights.getTravelDate());
        flights.setAvailableMaleSeats(flightsDTO.getAvailableMaleSeats() != null ? flightsDTO.getAvailableMaleSeats() : flights.getAvailableMaleSeats());
        flights.setAvailableFemaleSeats(flightsDTO.getAvailableFemaleSeats() != null ? flightsDTO.getAvailableFemaleSeats() : flights.getAvailableFemaleSeats());

        return new FlightsDTO(flightRepository.save(flights));
    }

    // Delete flight by ID
    @Override
    public void deleteFlight(Integer id) {
        Flights flights = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight does not exist with id: " + id));
        flightRepository.delete(flights);
    }

    // Delete all flights
    @Override
    public void deleteAll() {
        flightRepository.deleteAll();
    }
}

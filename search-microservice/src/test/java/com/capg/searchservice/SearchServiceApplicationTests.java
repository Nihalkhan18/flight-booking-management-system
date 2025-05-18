package com.capg.searchservice;

import com.capg.dto.FlightsDTO;
import com.capg.entity.Flights;
import com.capg.repository.FlightRepository;
import com.capg.service.FlightServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class SearchServiceApplicationTests {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    @Test
    void contextLoads() {
    }

    @Test
    void testDoesFlightExistById() {
        Flights flights = new Flights(
                20,
                "Akasa",
                "Chennai",
                "Calicut",
                "12.30",
                "2.45",
                58,
                3600,
                LocalDate.of(2025, 5, 20),
                29,
                29
        );

        when(flightRepository.findById(20)).thenReturn(Optional.of(flights));

        FlightsDTO found = flightService.getFlight(20);

        assertThat(found).isNotNull();
        assertEquals("Akasa", found.getFlightName());
        verify(flightRepository, times(1)).findById(20);
    }

    @Test
    void testSaveFlight() {
        Flights flights = new Flights(
                21,
                "Indigo",
                "Chennai",
                "Cochin",
                "12.30",
                "2.45",
                62,
                3500,
                LocalDate.of(2025, 5, 21),
                31,
                31
        );

        when(flightRepository.save(any(Flights.class))).thenReturn(flights);

        FlightsDTO dto = new FlightsDTO(flights);
        FlightsDTO saved = flightService.newFlight(dto);

        assertEquals("Indigo", saved.getFlightName());
        verify(flightRepository, times(1)).save(any(Flights.class));
    }

    @Test
    void testGetAllFlights() {
        List<Flights> flightsList = Arrays.asList(
                new Flights(20, "Akasa", "Chennai", "Calicut", "12.30", "2.45", 58, 3600, LocalDate.of(2025,5,20), 29, 29),
                new Flights(21, "Indigo", "Calicut", "Bangalore", "6.30", "8.45", 26, 3200, LocalDate.of(2025,5,20), 13, 13),
                new Flights(22, "Indigo", "Calicut", "Bangalore", "8.35", "10.45", 43, 3250, LocalDate.of(2025,5,20), 22, 21)
        );

        when(flightRepository.findAll()).thenReturn(flightsList);

        List<FlightsDTO> flightsDTOList = flightService.getFlights();

        assertEquals(3, flightsDTOList.size());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testDeleteFlight() {
        Flights flight = new Flights(30, "Akasa", "Chennai", "Calicut", "12.30", "2.45", 58, 3600, LocalDate.of(2025,5,20), 29, 29);

        when(flightRepository.findById(30)).thenReturn(Optional.of(flight));

        flightService.deleteFlight(30);

        verify(flightRepository, times(1)).delete(flight);
    }
}

package com.capg.entity;

import javax.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "flights")
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private int flightId;

    @Column(name = "flight_name")
    private String flightName;

    @Column(name = "airline")
    private String airline;

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    // One flight can have many bookings (One-to-Many relationship)
    @OneToMany(mappedBy = "flights", fetch = FetchType.LAZY)
    private List<BookingDetails> bookingDetails;

	public void setAvailableSeats(int i) {
		// TODO Auto-generated method stub
		
	}
}

package com.capg.entity;

import com.capg.dto.BookingDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking_details") // This is your table name in MySQL
public class BookingDetails {

    public static final String SEQUENCE_NAME = null;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated primary key
    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "required_seats")
    private Integer requiredSeats;

    @Column(name = "flight_id")
    private int flightId;

    @ManyToOne(fetch = FetchType.LAZY) // Assuming there is a flight entity linked to this
    @JoinColumn(name = "flight_id", insertable = false, updatable = false) // Foreign key constraint
    private Flights flights;

    @Column(name = "booked_on")
    private LocalDateTime bookedOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    // Constructor to convert from DTO to entity
    public BookingDetails(BookingDetailsDTO bookingDetailsDTO) {
        this.bookingId = bookingDetailsDTO.getBookingId();
        this.firstName = bookingDetailsDTO.getFirstName();
        this.lastName = bookingDetailsDTO.getLastName();
        this.gender = bookingDetailsDTO.getGender();
        this.email = bookingDetailsDTO.getEmail();
        this.phoneNo = bookingDetailsDTO.getPhoneNo();
        this.requiredSeats = bookingDetailsDTO.getRequiredSeats();
        this.flightId = bookingDetailsDTO.getFlightId();
        this.bookedOn = bookingDetailsDTO.getBookedOn();
        this.updatedOn = bookingDetailsDTO.getUpdatedOn();
    }

    // Automatically set the booked time when the booking is created
    public void bookedTime() {
        this.bookedOn = LocalDateTime.now();
    }

    // Automatically set the updated time when the record is updated
    public void updatedTime() {
        this.updatedOn = LocalDateTime.now();
    }
}

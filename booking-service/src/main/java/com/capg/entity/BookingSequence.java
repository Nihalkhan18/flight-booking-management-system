package com.capg.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking_sequence") // This specifies the table name in MySQL
public class BookingSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate the primary key (if needed)
    @Column(name = "id")
    private Long id;  // Use Long for auto-generated ID (could be Integer or other types as per your requirement)

    @Column(name = "sequence")
    private int sequence;  // Sequence value (used to store the next sequence number for bookings)
}

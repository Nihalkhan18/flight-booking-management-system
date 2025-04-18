package com.capg.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "db_sequence")  // Table name in MySQL
public class DbSequence {

    @Id
    private String id;  // Use the sequence name as the primary key
    private int sequence;  // Sequence value

    public DbSequence() {
    }

    public DbSequence(String id, int sequence) {
        this.id = id;
        this.sequence = sequence;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}

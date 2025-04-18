package com.capg.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class DbSequence {
    
    @Id
    private String id;
    private int sequence;

    

	// Getters and setters
    public String getId(String sequenceName, int i) {
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

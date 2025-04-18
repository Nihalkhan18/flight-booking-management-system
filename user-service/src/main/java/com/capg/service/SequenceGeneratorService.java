package com.capg.service;

import com.capg.entity.DbSequence;
import com.capg.repository.DbSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SequenceGeneratorService {

    @Autowired
    private DbSequenceRepository dbSequenceRepository;

    // Method to get and increment sequence number
    @Transactional
    public int getSequenceNumber(String sequenceName) {

        DbSequence sequence = dbSequenceRepository.findById(sequenceName)
                .orElse(new DbSequence(sequenceName, 1));  // Default value if not found

        // Increment the sequence
        sequence.setSequence(sequence.getSequence() + 1);

        // Save the updated sequence value back to the database
        dbSequenceRepository.save(sequence);

        return sequence.getSequence();
    }
}

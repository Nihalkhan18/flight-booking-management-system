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

    @Transactional
    public int getSequenceNumber(String sequenceName) {

        DbSequence sequence = dbSequenceRepository.findById(sequenceName)
                .orElse(new DbSequence());

        sequence.setSequence(sequence.getSequence() + 1);
        dbSequenceRepository.save(sequence);

        return sequence.getSequence();
    }
}

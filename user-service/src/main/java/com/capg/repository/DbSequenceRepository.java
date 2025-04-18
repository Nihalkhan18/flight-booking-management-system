package com.capg.repository;

import com.capg.entity.DbSequence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbSequenceRepository extends JpaRepository<DbSequence, String> {
    // JpaRepository provides methods like save(), findById(), etc.
}

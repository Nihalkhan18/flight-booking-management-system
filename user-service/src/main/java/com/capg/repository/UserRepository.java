package com.capg.repository;

import com.capg.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer> {

    // Method to get UserData by Email
    Optional<UserData> getUserDataByEmail(String email);

    // Method to get UserData by Username
    Optional<UserData> getUserDataByUsername(String username);

	UserData findByUsername(String username);
}

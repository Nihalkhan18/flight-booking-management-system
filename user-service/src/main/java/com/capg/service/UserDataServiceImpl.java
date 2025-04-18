package com.capg.service;

import com.capg.dto.UserDataDTO;
import com.capg.entity.UserData;
import com.capg.exception.UserNotFoundException;
import com.capg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDataServiceImpl implements UserDataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    // Get all users
    @Override
    public List<UserDataDTO> getUserData() {
        List<UserData> userData = userRepository.findAll();
        return userData.stream().map(UserDataDTO::new).collect(Collectors.toList());
    }

    // Load user by username for authentication
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData = userRepository.findByUsername(username);

        if (userData == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Now compare the provided password with the stored hash using BCryptPasswordEncoder
        return new org.springframework.security.core.userdetails.User(userData.getUsername(), 
            userData.getUserPassword(), new ArrayList<>());
    }

    // Get user with the given ID
    @Override
    public UserDataDTO getUser(Integer id) {
        UserData userData = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with ID: " + id));
        return new UserDataDTO(userData);
    }

    // Create new user
    @Override
    public UserDataDTO newUser(UserDataDTO userDataDTO) {
        UserData userData = new UserData(userDataDTO);

        // Hash the password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userDataDTO.getUserPassword());
        userData.setUserPassword(hashedPassword);

        // Optionally, if you need to set a unique sequence ID for the user
        // userData.setUserId(sequenceGeneratorService.getSequenceNumber(UserData.SEQUENCE_NAME));

        return new UserDataDTO(userRepository.save(userData));
    }

    // Update user data for the given ID
    @Override
    public UserDataDTO updateUser(Integer id, UserDataDTO userDataDTO) {
        UserData userData = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with ID: " + id));

        // Update the fields if new values are provided
        if (userDataDTO.getUsername() != null) {
            userData.setUsername(userDataDTO.getUsername());
        }
        if (userDataDTO.getPhoneNo() != null) {
            userData.setPhoneNo(userDataDTO.getPhoneNo());
        }
        if (userDataDTO.getEmail() != null) {
            userData.setEmail(userDataDTO.getEmail());
        }
        if (userDataDTO.getUserPassword() != null) {
            // Hash the password before saving (if password is being updated)
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(userDataDTO.getUserPassword());
            userData.setUserPassword(hashedPassword);
        }

        // Save and return the updated user
        return new UserDataDTO(userRepository.save(userData));
    }

    // Delete user data for the given ID
    @Override
    public void deleteUser(Integer id) {
        UserData userData = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with ID:" + id));
        userRepository.delete(userData);
    }

    // Delete all user data
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}

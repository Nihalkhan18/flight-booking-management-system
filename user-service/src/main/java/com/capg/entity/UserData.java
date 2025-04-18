package com.capg.entity;

import com.capg.dto.UserDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_data")  // Specify the table name in MySQL
public class UserData {

    public static String SEQUENCE_NAME;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremented in MySQL
    private int userId;

    private String username;
    private String phoneNo;
    private String email;
    private String userPassword;

    // Constructor to map UserDataDTO to UserData entity
    public UserData(UserDataDTO userDataDTO) {
        this.userId = userDataDTO.getUserId();
        this.username = userDataDTO.getUsername();
        this.phoneNo = userDataDTO.getPhoneNo();
        this.email = userDataDTO.getEmail();
        this.userPassword = userDataDTO.getUserPassword();
    }
}

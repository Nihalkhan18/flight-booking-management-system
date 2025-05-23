package com.capg.controller;

import com.capg.dto.UserDataDTO;
import com.capg.repository.UserRepository;
import com.capg.service.UserDataService;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataService userDataService;

    @PostMapping("/create")
    public ResponseEntity<UserDataDTO> createUser(@Valid @RequestBody UserDataDTO userDataDTO) {
        return new ResponseEntity<UserDataDTO>(userDataService.newUser(userDataDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public List<UserDataDTO> userData() {
        return userDataService.getUserData();
    }

    @GetMapping("/get/{id}")
    public UserDataDTO userById(@PathVariable Integer id) {
        return userDataService.getUser(id);
    }
    
//     public User updateUser(@PathVariable Integer id, @RequestBody User u ) {
//    	 return service.updateUser(id,u);
//     }
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDataDTO> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDataDTO userDataDTO) {
        return new ResponseEntity<UserDataDTO>(userDataService.updateUser(id, userDataDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userDataService.deleteUser(id);
        return "User with ID: " + id + "was deleted successfully";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        userDataService.deleteAll();
        return "All user data was deleted successfully";
    }
}

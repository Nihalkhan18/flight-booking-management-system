package com.capg.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capg.security.models.AuthenticationRequest;
import com.capg.security.models.AuthenticationResponse;
import com.capg.security.util.JwtUtil;
import com.capg.service.UserDataServiceImpl;

@RestController
public class SecurityController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserDataServiceImpl usd;

    // Constructor-based dependency injection
    @Autowired
    public SecurityController(AuthenticationManager authenticationManager, 
                              UserDetailsService userDetailsService, 
                              JwtUtil jwtUtil , UserDataServiceImpl usd) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.usd=usd;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            // Authenticate the user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            // Load the user details
            UserDetails userDetails = usd.loadUserByUsername(authenticationRequest.getUsername());
            
            // Generate JWT token
            String jwt = jwtUtil.generateToken(userDetails);

            // Return the JWT token
            return ResponseEntity.ok(new AuthenticationResponse(jwt));

        } catch (BadCredentialsException e) {
            // Return a proper error response when credentials are wrong
            return ResponseEntity.status(401).body("Incorrect Username or Password");
        } catch (Exception e) {
            // Handle other exceptions and return a server error
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}

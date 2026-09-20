package com.crud.controller;

import com.crud.entity.AuthRequest;
import com.crud.entity.Employee;
import com.crud.service.AuthRequestService;
import com.crud.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/access")
public class AccessController {


    private final AuthRequestService authRequestService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AccessController(AuthRequestService authRequestService, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authRequestService = authRequestService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }



    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody AuthRequest authRequest) {
        log.info("Received request to create employee: {}", authRequest);
        if(authRequest.getPassword() != null && !authRequest.getPassword().isBlank()) {
            authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        }
        AuthRequest authData = authRequestService.saveAuthRequest(authRequest);
        return ResponseEntity.ok(authData);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getName());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }
}

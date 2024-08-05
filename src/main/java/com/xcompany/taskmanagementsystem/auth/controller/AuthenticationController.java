package com.xcompany.taskmanagementsystem.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xcompany.taskmanagementsystem.auth.dto.AuthenticationRequest;
import com.xcompany.taskmanagementsystem.auth.dto.AuthenticationResponse;
import com.xcompany.taskmanagementsystem.auth.dto.RegisterRequest;
import com.xcompany.taskmanagementsystem.auth.exception.BadCredentialsException;
import com.xcompany.taskmanagementsystem.auth.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "JWT-security", description = "Methods for registration and authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login", produces="application/json")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {

        if (!authenticationService.isCredentialsValid(authenticationRequest)){
            throw new BadCredentialsException();
        }

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping(value = "/register", produces="application/json")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest registerRequest) {

        if (authenticationService.userExists(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        AuthenticationResponse response = authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

package com.xcompany.taskmanagementsystem.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.xcompany.taskmanagementsystem.api.model.Role;
import com.xcompany.taskmanagementsystem.api.model.User;
import com.xcompany.taskmanagementsystem.auth.dto.AuthenticationRequest;
import com.xcompany.taskmanagementsystem.auth.dto.AuthenticationResponse;
import com.xcompany.taskmanagementsystem.auth.dto.RegisterRequest;
import com.xcompany.taskmanagementsystem.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.
                builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .get();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public boolean userExists(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public boolean isCredentialsValid(AuthenticationRequest authenticationRequest) {
        String reqUsername = authenticationRequest.getUsername();
        String reqPassword = authenticationRequest.getPassword();

        String dbPassword = userRepository.findByUsername(reqUsername)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"))
                .getPassword();

        return passwordEncoder.matches(reqPassword, dbPassword);
    }
}

package com.xcompany.taskmanagementsystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.xcompany.taskmanagementsystem.api.model.Role;
import com.xcompany.taskmanagementsystem.api.model.User;
import com.xcompany.taskmanagementsystem.auth.dto.AuthenticationResponse;
import com.xcompany.taskmanagementsystem.auth.dto.RegisterRequest;
import com.xcompany.taskmanagementsystem.auth.service.AuthenticationService;
import com.xcompany.taskmanagementsystem.auth.service.JwtService;
import com.xcompany.taskmanagementsystem.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest("user@example.com", "password");
        User expectedUser = User.builder().username("user@example.com").password("encodedPassword").role(Role.USER).build();

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertEquals("jwtToken", response.getToken());
    }
}

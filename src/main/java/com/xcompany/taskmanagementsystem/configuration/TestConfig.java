package com.xcompany.taskmanagementsystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.xcompany.taskmanagementsystem.auth.service.JwtService;

@Configuration
public class TestConfig {

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }
}

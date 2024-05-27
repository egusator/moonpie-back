package com.example.moonpie_back.core.config;

import jakarta.validation.ClockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class TimeConfiguration {

    @Bean
    public ClockProvider clockProvider() {
        return Clock::systemDefaultZone;
    }
}


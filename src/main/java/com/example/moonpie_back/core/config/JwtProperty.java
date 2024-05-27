package com.example.moonpie_back.core.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
@Data
@Component
public class JwtProperty {
    private Duration expirationTime;
    private String secret;
}

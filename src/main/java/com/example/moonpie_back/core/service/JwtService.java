package com.example.moonpie_back.core.service;

import com.example.moonpie_back.core.config.JwtProperty;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.ClockProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperty jwtProperty;
    private final ClockProvider clockProvider;

    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", "user");
        String token = createToken(claims, userId);
        return token;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(clockProvider.getClock().millis()))
                .expiration(
                        Date.from(
                                new Date(clockProvider.getClock().millis())
                                        .toInstant()
                                        .plus(jwtProperty.getExpirationTime())))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperty.getSecret()));
    }
}

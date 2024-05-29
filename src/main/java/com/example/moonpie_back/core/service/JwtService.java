package com.example.moonpie_back.core.service;

import com.example.moonpie_back.core.config.JwtProperty;
import io.jsonwebtoken.Claims;
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
import java.util.function.Function;
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

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public List<String> extractAuthorities(String token) {
        return extractClaim(token, claims -> claims.get("authorities", List.class));
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public void validate(String token) {
        try {
            if (Jwts.parser()
                    .verifyWith(getSigningKey()) // Выбросит JwtException,
                    .build()                     // если не сможет верифицировать
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(new Date(clockProvider.getClock().millis()))) {
                throw new RuntimeException("The token expired!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid token!");
        }
    }

    public Long validateAndGetAuthorities(String token) {
        validate(token);
        String userId = extractSubject(token);
        return Long.valueOf(userId);
    }

}

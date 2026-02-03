package com.rest.assignment.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {
    @Value("${jwt_secret}")
    private String SECRET_KEY;

    private Key getSignedKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(UUID userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId.toString());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignedKey(),
                        SignatureAlgorithm.HS256).compact();
    }

    public UUID extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String userId = claims.get("userId", String.class);
        return UUID.fromString(userId);
    }
}

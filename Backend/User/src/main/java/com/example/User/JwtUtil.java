package com.example.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY ="8e6f8332d7796574ffe343a7765db185a3a5c5ee15ee1e58ec5afaf0f77fcf25";

    public String generateToken(String username, String role) {
        Map<String, Object> claims = Map.of("role", role); // Add role to claims
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 3)) // 3 minutes
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()) // Use secure key
                .compact();
    }
}
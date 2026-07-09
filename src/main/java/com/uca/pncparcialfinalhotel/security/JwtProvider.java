package com.uca.pncparcialfinalhotel.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final int accessTokenExpirationMinutes;
    private final int refreshTokenExpirationDays;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration-minutes}") int accessTokenExpirationMinutes,
            @Value("${jwt.refresh-token-expiration-days}") int refreshTokenExpirationDays) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
        this.refreshTokenExpirationDays = refreshTokenExpirationDays;
    }

    public String generateAccessToken(Long userId, int tokenVersion, String username, String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("tv", tokenVersion) // Forma segura de agregar claims
                .claim("un", username)
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(accessTokenExpirationMinutes, ChronoUnit.MINUTES)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken() {
        Instant now = Instant.now();
        return Jwts.builder()
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(refreshTokenExpirationDays, ChronoUnit.DAYS)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public int getRefreshTokenExpirationDays() {
        return refreshTokenExpirationDays;
    }
}
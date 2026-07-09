package com.uca.pncparcialfinalhotel.service.impl;

import com.uca.pncparcialfinalhotel.entitys.RefreshToken;
import com.uca.pncparcialfinalhotel.entitys.User;
import com.uca.pncparcialfinalhotel.repository.RefreshTokenRepository;
import com.uca.pncparcialfinalhotel.repository.UserRepository;
import com.uca.pncparcialfinalhotel.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userRepository.findByUsername(username).orElseThrow();
        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getTokenVersion(), user.getUsername(), user.getRole().name());
        String refreshTokenString = jwtProvider.generateRefreshToken();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenString);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(60L * 60 * 24 * jwtProvider.getRefreshTokenExpirationDays()));
        refreshTokenRepository.save(refreshToken);
        return new AuthResponse(accessToken, refreshTokenString);
    }

    public AuthResponse refresh(String refreshTokenString) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenString)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        User user = refreshToken.getUser();
        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getTokenVersion(), user.getUsername(), user.getRole().name());
        // optionally issue a new refresh token
        return new AuthResponse(accessToken, refreshTokenString);
    }

    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setTokenVersion(user.getTokenVersion() + 1);
        userRepository.save(user);
        // invalidate all refresh tokens for this user
        refreshTokenRepository.deleteByUser(user);
    }

    public void changePasswordByUsername(String username, String newPassword) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        changePassword(user.getId(), newPassword);
    }

    // DTO
    public record AuthResponse(String accessToken, String refreshToken) {}
}



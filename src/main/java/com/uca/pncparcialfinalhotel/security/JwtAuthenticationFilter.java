package com.uca.pncparcialfinalhotel.security;

import com.uca.pncparcialfinalhotel.entitys.User;
import com.uca.pncparcialfinalhotel.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        if (!jwtProvider.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Jws<Claims> claimsJws = jwtProvider.parseToken(token);
        Claims claims = claimsJws.getBody();
        String userIdStr = claims.getSubject();
        Number tokenVersionNumber = claims.get("tv", Number.class);
        Integer tokenVersion = tokenVersionNumber == null ? null : tokenVersionNumber.intValue();
        String username = (String) claims.get("un");

        try {
            Long userId = Long.parseLong(userIdStr);
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            User user = userOpt.get();
            if (user.getTokenVersion() == null || !user.getTokenVersion().equals(tokenVersion)) {
                // token invalid due to version mismatch
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            // ignore and continue filter chain
        }

        filterChain.doFilter(request, response);
    }
}


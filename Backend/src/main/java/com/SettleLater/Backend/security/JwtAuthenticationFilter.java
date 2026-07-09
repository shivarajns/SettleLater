package com.SettleLater.Backend.security;

import com.SettleLater.Backend.auth.service.CustomUserDetailsService;
import com.SettleLater.Backend.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService
    ) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            String token = authHeader.substring(7).trim();

            if (!jwtService.isTokenValid(token)) {
                sendUnauthorizedResponse(
                        response,
                        "INVALID_OR_EXPIRED_TOKEN",
                        "Invalid or Expired Token"
                );
                return;
            }

            String email = jwtService.extractEmail(token);

            UserDetails userDetails =
                    customUserDetailsService.loadUserByUsername(email);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            sendUnauthorizedResponse(
                    response,
                    "INVALID_OR_EXPIRED_TOKEN",
                    "Invalid or Expired Token"
            );
        }
    }

    private void sendUnauthorizedResponse(
            HttpServletResponse response,
            String error,
            String message
    ) throws IOException {

        response.setHeader(
                "Access-Control-Allow-Origin",
                "http://localhost:3000"
        );

        response.setHeader(
                "Access-Control-Allow-Credentials",
                "true"
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String json = """
        {
            "validToken": false,
            "error": "%s",
            "message": "%s"
        }
        """.formatted(error, message);

        response.getWriter().write(json);
    }


}

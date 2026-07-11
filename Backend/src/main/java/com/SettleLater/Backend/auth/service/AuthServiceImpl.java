package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.auth.dto.CurrentUserResponseDTO;
import com.SettleLater.Backend.auth.model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public CurrentUserResponseDTO getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            throw new RuntimeException("User is not authenticated");
        }

        User user = (User) authentication.getPrincipal();

        return CurrentUserResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .isVerified(user.isVerified())
                .build();
    }
}
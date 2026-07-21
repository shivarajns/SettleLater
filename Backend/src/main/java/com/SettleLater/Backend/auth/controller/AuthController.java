package com.SettleLater.Backend.auth.controller;

import com.SettleLater.Backend.auth.dto.CurrentUserResponseDTO;
import com.SettleLater.Backend.auth.service.AuthService;
import com.SettleLater.Backend.common.ApiResponse.ApiResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<CurrentUserResponseDTO>> getCurrentUser() {

        CurrentUserResponseDTO currentUser = authService.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponseDTO.<CurrentUserResponseDTO>builder()
                        .success(true)
                        .message("Current user fetched successfully")
                        .data(authService.getCurrentUser())
                        .build()
        );
    }
}
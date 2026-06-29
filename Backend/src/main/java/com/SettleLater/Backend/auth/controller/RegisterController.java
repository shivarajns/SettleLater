package com.SettleLater.Backend.auth.controller;

import com.SettleLater.Backend.auth.dto.RegisterRequestDTO;
import com.SettleLater.Backend.auth.dto.RegisterResponseDTO;
import com.SettleLater.Backend.auth.service.UserRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {
    public final UserRegistrationService userRegesterationService;

    public RegisterController(UserRegistrationService userRegesterationService) {
        this.userRegesterationService = userRegesterationService;
    }

    @PostMapping
    public ResponseEntity<RegisterResponseDTO> registerUser(
            @RequestBody RegisterRequestDTO requestDTO
            ){
        RegisterResponseDTO registerUser = userRegesterationService.registerUser(requestDTO);
        return ResponseEntity.ok(registerUser);
    }
}

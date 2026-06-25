package com.SettleLater.Backend.auth.controller;

import com.SettleLater.Backend.auth.DTO.PasswordResetRequestDTO;
import com.SettleLater.Backend.auth.DTO.PasswordResetResponseDTO;
import com.SettleLater.Backend.auth.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/reset")
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/password")
    public ResponseEntity<PasswordResetResponseDTO> resetPassword(
            @RequestBody PasswordResetRequestDTO requestDTO
            ){
        PasswordResetResponseDTO responseDTO = passwordResetService.resetPassword(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}

package com.SettleLater.Backend.auth.controller;

import com.SettleLater.Backend.auth.dto.PasswordResetMailResponseDTO;
import com.SettleLater.Backend.auth.service.PasswordResetMailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/reset")
public class PasswordResetMailController {
    private final PasswordResetMailService passwordResetService;

    public PasswordResetMailController(PasswordResetMailService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/password")
    public ResponseEntity<PasswordResetMailResponseDTO> resetPassword(
            @RequestParam String email
    ){
        PasswordResetMailResponseDTO responseDTO = passwordResetService.resetPassword(email);
        return ResponseEntity.ok(responseDTO);
    }
}

package com.SettleLater.Backend.auth.controller;

import com.SettleLater.Backend.auth.dto.EmailVerifyResponseDTO;
import com.SettleLater.Backend.auth.dto.WeatherEmailVerifiedResponseDTO;
import com.SettleLater.Backend.auth.service.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify")
public class EmailVerificationController {
    private final EmailVerificationService emailVerificationService;

    public EmailVerificationController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @GetMapping
    public ResponseEntity<EmailVerifyResponseDTO> verifyEmail(
            @RequestParam String token
    ) {
        EmailVerifyResponseDTO responseDTO = emailVerificationService.verifyEmail(token);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("get")
    public ResponseEntity<WeatherEmailVerifiedResponseDTO> isVerified(
            Authentication authentication
            ){
        String email = authentication.getName();
        WeatherEmailVerifiedResponseDTO responseDTO = emailVerificationService.checkIsEmailVerified(email);
        return ResponseEntity.ok(responseDTO);
    }
}

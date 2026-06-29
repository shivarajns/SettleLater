package com.SettleLater.Backend.auth.controller;

import com.SettleLater.Backend.auth.dto.EmailVerifyResponseDTO;
import com.SettleLater.Backend.auth.service.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

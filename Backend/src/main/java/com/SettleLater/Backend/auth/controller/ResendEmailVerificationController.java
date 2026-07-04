package com.SettleLater.Backend.auth.controller;

import com.SettleLater.Backend.auth.dto.ResendEmailVerificationResponseDTO;
import com.SettleLater.Backend.auth.service.ResendEmailVerificationService;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/verify")
public class ResendEmailVerificationController {
    private final ResendEmailVerificationService resendEmailVerificationService;

    public ResendEmailVerificationController(ResendEmailVerificationService resendEmailVerificationService) {
        this.resendEmailVerificationService = resendEmailVerificationService;
    }

    @GetMapping("/resend")
    public ResponseEntity<ResendEmailVerificationResponseDTO> resendVerification(
            Authentication authentication
    ){
        String email = authentication.getName();
        System.out.println(email);
        ResendEmailVerificationResponseDTO response = resendEmailVerificationService.resendVerification(email);
        return ResponseEntity.ok(response);
    }
}

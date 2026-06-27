package com.SettleLater.Backend.auth.controller;

import com.SettleLater.Backend.auth.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class JwtController {
    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generateToken(@RequestParam String email){
        String token = jwtService.generateToken(email);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam String token){
        String email = jwtService.extractEmail(token);
        return ResponseEntity.ok(email);
    }
}

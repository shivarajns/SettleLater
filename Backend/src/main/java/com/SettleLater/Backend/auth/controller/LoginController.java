package com.SettleLater.Backend.auth.controller;

import com.SettleLater.Backend.auth.dto.LoginRequestDTO;
import com.SettleLater.Backend.auth.dto.LoginResponseDTO;
import com.SettleLater.Backend.auth.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request
            ) {
        LoginResponseDTO response = loginService.login(request);
        return ResponseEntity.ok(response);
    }
}

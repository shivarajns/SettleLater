package com.SettleLater.Backend.auth.controller;

import com.SettleLater.Backend.auth.DTO.LoginRequestDTO;
import com.SettleLater.Backend.auth.DTO.LoginResponseDTO;
import com.SettleLater.Backend.auth.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request
            ) {
        LoginResponseDTO response = loginService.login(request);
        return ResponseEntity.ok(response);
    }
}

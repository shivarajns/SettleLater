package com.SettleLater.Backend.Login;

import com.SettleLater.Backend.auth.dto.LoginRequestDTO;
import com.SettleLater.Backend.auth.dto.LoginResponseDTO;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.UserRepository;
import com.SettleLater.Backend.auth.service.JwtService;
import com.SettleLater.Backend.auth.service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginService loginService;

    @Test
    void loginSuccess() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("hashedPass");
        user.setVerified(true);

        LoginRequestDTO req = new LoginRequestDTO();
        req.setEmail("user@example.com");
        req.setPassword("1234");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "hashedPass")).thenReturn(true);
        when(jwtService.generateToken("user@example.com")).thenReturn("jwt-token");

        LoginResponseDTO res = loginService.login(req);

        assertEquals("Login Success", res.getMessage());
        assertEquals("jwt-token", res.getToken());
        assertTrue(res.getEmailVerified());
    }

    @Test
    void loginWrongPassword() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("hashedPass");

        LoginRequestDTO req = new LoginRequestDTO();
        req.setEmail("user@example.com");
        req.setPassword("wrong");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashedPass")).thenReturn(false);

        LoginResponseDTO res = loginService.login(req);

        assertEquals("Wrong Credentials.", res.getMessage());
        assertNull(res.getToken());
        assertNull(res.getEmailVerified());
    }

    @Test
    void buildShouldFail_intentionalFailure() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("hashedPass");
        user.setVerified(true);

        LoginRequestDTO req = new LoginRequestDTO();
        req.setEmail("user@example.com");
        req.setPassword("1234");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "hashedPass")).thenReturn(true);
        when(jwtService.generateToken("user@example.com")).thenReturn("jwt-token");

        LoginResponseDTO res = loginService.login(req);

        // Intentionally wrong assertion to break the CI build
        assertEquals("This Should Fail CI", res.getMessage());
    }
}
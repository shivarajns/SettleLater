package com.SettleLater.Backend.authTest;

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
class UserLoginTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginService loginService;

    @Test
    void shouldReturnLoginSuccess() {

        // Arrange
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("shiva@gmail.com");
        request.setPassword("123456");

        User user = new User();
        user.setEmail("shiva@gmail.com");
        user.setPassword("encodedPassword");
        user.setVerified(true);
        user.setActive(true);

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        when(passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()))
                .thenReturn(true);

        when(jwtService.generateToken(request.getEmail()))
                .thenReturn("jwt-token");

        // Act
        LoginResponseDTO response = loginService.login(request);

        // Assert
        assertEquals("Login Success", response.getMessage());
        assertEquals("jwt-token", response.getToken());
        assertTrue(response.getEmailVerified());

        verify(userRepository).findByEmail(request.getEmail());
        verify(userRepository).existsByEmail(request.getEmail());
        verify(passwordEncoder, times(2))
                .matches(request.getPassword(), user.getPassword());
        verify(jwtService).generateToken(request.getEmail());
    }

    // Wrong Credentials.
    @Test
    void shouldReturnWrongCredential(){
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("shiva@gmail.com");
        request.setPassword("123456");

        User user = new User();
        user.setEmail("shiva@gmail.com");
        user.setPassword("encodedPassword");
        user.setVerified(true);

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(false);


        LoginResponseDTO response = loginService.login(request);

        assertEquals("Wrong Credentials.", response.getMessage());
        assertNull(response.getToken());
    }

    // User Not Found With this Email
    @Test
    void shouldReturnEmailNotRegistered(){
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("tester@gmail.com");
        request.setPassword("123456");

        User user = new User();
        user.setEmail("developer@gmail.com");
        user.setPassword("encodedPassword");
        user.setVerified(true);

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        LoginResponseDTO responseDTO =loginService.login(request);

        assertEquals("User Not Found With This Email.", responseDTO.getMessage());
        assertNull(responseDTO.getEmailVerified());
        assertNull(responseDTO.getToken());
    }

    // Please verify your email before logging in.
    @Test
    void shouldReturnVerifyEmail(){
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("tester@gmail.com");
        request.setPassword("123456");

        User user = new User();
        user.setEmail("developer@gmail.com");
        user.setPassword("encodedPassword");
        user.setVerified(false);

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(true);

        when(jwtService.generateToken(request.getEmail()))
                .thenReturn("asdfghjkl");


        LoginResponseDTO response = loginService.login(request);

        assertEquals("Please verify your email before logging in.", response.getMessage());
        assertEquals("asdfghjkl", response.getToken());
        assertFalse(response.getEmailVerified());
    }
}
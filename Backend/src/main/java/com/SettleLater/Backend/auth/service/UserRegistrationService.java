package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.auth.DTO.RegisterRequestDTO;
import com.SettleLater.Backend.auth.DTO.RegisterResponseDTO;
import com.SettleLater.Backend.auth.model.EmailVerificationToken;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.EmailVerificationTokenRepository;
import com.SettleLater.Backend.auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRegistrationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    public UserRegistrationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    public RegisterResponseDTO registerUser(RegisterRequestDTO requestDTO){

        if(userRepository.existsByEmail(requestDTO.getEmail())){
            RegisterResponseDTO responseDTO = new RegisterResponseDTO();
            responseDTO.setMessage("Email already Register");
            return responseDTO;
        }

        User user = new User();
        user.setEmail(requestDTO.getEmail());
        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setUserId(user);
        emailVerificationToken.setToken(token);
        emailVerificationTokenRepository.save(emailVerificationToken);

        RegisterResponseDTO  responseDTO = new RegisterResponseDTO();
        responseDTO.setMessage("Register Successfully");

        return responseDTO;
    }
}

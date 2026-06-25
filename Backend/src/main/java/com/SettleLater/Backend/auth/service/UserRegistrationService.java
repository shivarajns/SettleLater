package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.Common.EmailSender.EmailSenderService;
import com.SettleLater.Backend.auth.DTO.RegisterRequestDTO;
import com.SettleLater.Backend.auth.DTO.RegisterResponseDTO;
import com.SettleLater.Backend.auth.model.EmailVerificationToken;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.EmailVerificationTokenRepository;
import com.SettleLater.Backend.auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserRegistrationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final EmailSenderService emailSenderService;

    public UserRegistrationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailVerificationTokenRepository emailVerificationTokenRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.emailSenderService = emailSenderService;
    }
    @Transactional
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

        String verificationLink = "http://localhost:8080/auth/verify?token="+token;

        emailSenderService.sendVarificationEmail(user.getEmail(), verificationLink);

        RegisterResponseDTO  responseDTO = new RegisterResponseDTO();
        responseDTO.setMessage("Register Successfully");

        return responseDTO;
    }
}

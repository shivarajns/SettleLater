package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.Common.EmailSender.EmailSenderService;
import com.SettleLater.Backend.auth.DTO.PasswordResetMailResponseDTO;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetMailService {
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    public PasswordResetMailService(UserRepository userRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    public PasswordResetMailResponseDTO resetPassword(String email){
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User with Email Not found")
        );

        if(!userRepository.existsByEmail(email)){
            PasswordResetMailResponseDTO responseDTO = new PasswordResetMailResponseDTO();
            responseDTO.setMessage("Email is not registered");
        }

        String userId= user.getUserId();
        String passwordRestLink = "http://localhost:8080/auth/reset/password/"+userId;
        emailSenderService.passwordResetMail(email, passwordRestLink);

        PasswordResetMailResponseDTO responseDTO = new PasswordResetMailResponseDTO();
        responseDTO.setMessage("Password Reset Link send to "+email);
        return responseDTO;
    }
}

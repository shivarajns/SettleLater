package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.Common.EmailSender.EmailSenderService;
import com.SettleLater.Backend.auth.dto.ResendEmailVerificationResponseDTO;
import com.SettleLater.Backend.auth.exceptions.TokenNotFound;
import com.SettleLater.Backend.auth.exceptions.UserNotFoundWithEmail;
import com.SettleLater.Backend.auth.model.EmailVerificationToken;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.EmailVerificationTokenRepository;
import com.SettleLater.Backend.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ResendEmailVerificationService {
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;

    public ResendEmailVerificationService(EmailVerificationTokenRepository emailVerificationTokenRepository, UserRepository userRepository, EmailSenderService emailSenderService) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    public ResendEmailVerificationResponseDTO resendVerification(String email){
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UserNotFoundWithEmail("User not found with this Email")
        );

        EmailVerificationToken verification = emailVerificationTokenRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(()-> new TokenNotFound("User token not Found"));

        String token = verification.getToken();
        String verificationLink = "http://localhost:8080/auth/verify?token="+token;

        emailSenderService.sendVarificationEmail(email,verificationLink);

        ResendEmailVerificationResponseDTO response = new ResendEmailVerificationResponseDTO();
        response.setMessage("Verification Email Send");

        return response;
    }
}

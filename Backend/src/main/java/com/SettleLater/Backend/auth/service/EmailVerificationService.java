package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.auth.DTO.EmailVerifyResponseDTO;
import com.SettleLater.Backend.auth.Exceptions.TokenNotFound;
import com.SettleLater.Backend.auth.model.EmailVerificationToken;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.EmailVerificationTokenRepository;
import com.SettleLater.Backend.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;

    public EmailVerificationService(EmailVerificationTokenRepository emailVerificationTokenRepository, UserRepository userRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.userRepository = userRepository;
    }

    public EmailVerifyResponseDTO verifyEmail(String token){
        EmailVerificationToken verifyEmail = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(
                        ()-> new TokenNotFound("User Token not found")
                );

        if(!emailVerificationTokenRepository.existsByToken(token)){
            EmailVerifyResponseDTO responseDTO = new EmailVerifyResponseDTO();
            responseDTO.setMessage("Token not found");
        }

        User user = verifyEmail.getUserId();
        user.setVerified(true);

        userRepository.save(user);

        EmailVerifyResponseDTO responseDTO = new EmailVerifyResponseDTO();
        responseDTO.setMessage("Email Verified");

        return responseDTO;
    }
}

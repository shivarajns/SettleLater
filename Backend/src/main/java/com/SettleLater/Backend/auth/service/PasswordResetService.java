package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.Common.EmailSender.EmailSenderService;
import com.SettleLater.Backend.auth.dto.PasswordResetRequestDTO;
import com.SettleLater.Backend.auth.dto.PasswordResetResponseDTO;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PasswordResetService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;

    public PasswordResetService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }

    public PasswordResetResponseDTO resetPassword(PasswordResetRequestDTO requestDTO){
        User user = userRepository.findByUserId(requestDTO.getUserId()).orElseThrow(
                ()-> new UsernameNotFoundException("User not Found")
        );

        if(!userRepository.existsByUserId(requestDTO.getUserId())){
            PasswordResetResponseDTO responseDTO = new PasswordResetResponseDTO();
            responseDTO.setMessage("you are not allowed to Reset your password.");
        }

        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(user);
        emailSenderService.sendSuccessMail(user.getEmail(), "Password Reset Successfully", "" +
                "Your password reset request has been successfully completed on "+ LocalDate.now());
        PasswordResetResponseDTO responseDTO = new PasswordResetResponseDTO();
        responseDTO.setMessage("Password Reset Successfully, go back and Login with your new password");

        return responseDTO;
    }
}

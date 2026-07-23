package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.auth.dto.LoginRequestDTO;
import com.SettleLater.Backend.auth.dto.LoginResponseDTO;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.EmailVerificationTokenRepository;
import com.SettleLater.Backend.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }



    public LoginResponseDTO login(LoginRequestDTO request){
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if(user == null){
            return new LoginResponseDTO(
                    "Wrong Credentials",
                    null, null

            );
        }

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )){
            return new LoginResponseDTO(
                    "Wrong Credentials.",
                    null, null
            );

        }

        String accessToken = jwtService.generateToken(
                request.getEmail()
        );

        if(passwordEncoder.matches(request.getPassword(), user.getPassword()) && !user.isVerified()){
            return new LoginResponseDTO(
                    "Please verify your email before logging in.",
                    accessToken, false);
        }

        return new LoginResponseDTO("Login Success"
                , accessToken, true);
    }
}

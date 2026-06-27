package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.auth.DTO.LoginRequestDTO;
import com.SettleLater.Backend.auth.DTO.LoginResponseDTO;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()-> new UsernameNotFoundException("User Not found with Email.")
        );

        if(!userRepository.existsByEmail(request.getEmail())){
            return new LoginResponseDTO(
                    "User Not Found With This Email.",
                    "NA"
            );
        }

        if(!user.isVerified()){
            return new LoginResponseDTO(
                    "Please verify your email before logging in.",
                    "NA"
            );
        }

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )){
            return new LoginResponseDTO(
                    "Wrong Credentials.",
                    "NA"
            );

        }

        String accessToken = jwtService.generateToken(
                request.getEmail()
        );

        return new LoginResponseDTO("Login Success"
                , accessToken);
    }
}

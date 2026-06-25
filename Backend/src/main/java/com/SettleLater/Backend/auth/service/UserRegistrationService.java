package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.auth.DTO.RegisterRequestDTO;
import com.SettleLater.Backend.auth.DTO.RegisterResponseDTO;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        RegisterResponseDTO  responseDTO = new RegisterResponseDTO();
        responseDTO.setMessage("Register Successfully");

        return responseDTO;
    }
}

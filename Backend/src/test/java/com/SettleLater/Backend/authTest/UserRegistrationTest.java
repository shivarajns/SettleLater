package com.SettleLater.Backend.authTest;

import com.SettleLater.Backend.common.EmailSender.EmailSenderService;
import com.SettleLater.Backend.auth.dto.RegisterRequestDTO;
import com.SettleLater.Backend.auth.dto.RegisterResponseDTO;
import com.SettleLater.Backend.auth.model.EmailVerificationToken;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.EmailVerificationTokenRepository;
import com.SettleLater.Backend.auth.repository.UserRepository;
import com.SettleLater.Backend.auth.service.UserRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private UserRegistrationService userRegistrationService;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(
                userRegistrationService,
                "BASE_URL",
                "http://localhost:8080"
        );
    }

    //Successful Registration.
    @Test
    void shouldRegisterUserSuccessfully(){
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setEmail("shiva@gmail.com");
        request.setUserName("shivaraj");
        request.setPhoneNumber("9901711038");
        request.setPassword("123456");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByPhoneNumber(request.getPhoneNumber()))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("passwordEncoded");

        RegisterResponseDTO response = userRegistrationService.registerUser(request);

        assertEquals("Register Successfully", response.getMessage());

        verify(userRepository, times(1))
                .save(any(User.class));

        verify(emailVerificationTokenRepository, times(1))
                .save(any(EmailVerificationToken.class));

        verify(emailSenderService, times(1))
                .sendVarificationEmail(
                        eq("shiva@gmail.com"),
                        contains("token=")
                );
    }


    // Email already Exists
    @Test
    void shouldReturnEmailAlreadyExistsWhenEmailExists(){
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setEmail("shiva@gmail.com");

        when(userRepository.existsByEmail("shiva@gmail.com"))
                .thenReturn(true);

        RegisterResponseDTO response = userRegistrationService.registerUser(request);

        assertEquals("Email already Register", response.getMessage());

        verify(userRepository, never()).save(any());
        verify(emailVerificationTokenRepository, never()).save(any());
        verify(emailSenderService, never()).sendVarificationEmail(anyString(), anyString());
    }

    // Phone Number Already Exists
    @Test
    void shouldReturnPhoneAlreadyExists(){
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setPhoneNumber("9089687951");

        when(userRepository.existsByPhoneNumber("9089687951"))
                .thenReturn(true);

        RegisterResponseDTO response = userRegistrationService.registerUser(request);

        assertEquals("Phone Number Already Register", response.getMessage());

        verify(userRepository, never()).save(any());
        verify(emailSenderService, never()).sendVarificationEmail(anyString(), anyString());
        verify(emailVerificationTokenRepository, never()).save(any());
    }

    // Check weather password is Encoding
    @Test
    void isPasswordIsEncoding(){
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUserName("shiva");
        request.setEmail("shiva@gmail.com");
        request.setPhoneNumber("1234567890");
        request.setPassword("123456");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByPhoneNumber(request.getPhoneNumber()))
                .thenReturn(false);

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("passwordEncoded");

        ArgumentCaptor<User> capture = ArgumentCaptor.forClass(User.class);
        userRegistrationService.registerUser(request);

        verify(userRepository).save(capture.capture());
        User saved = capture.getValue();

        assertEquals("passwordEncoded",
                saved.getPassword());

        RegisterResponseDTO response = userRegistrationService.registerUser(request);


    }

}

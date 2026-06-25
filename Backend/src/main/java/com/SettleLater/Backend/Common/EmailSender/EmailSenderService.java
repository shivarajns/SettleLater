package com.SettleLater.Backend.Common.EmailSender;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendVarificationEmail(
            String to,
            String verificationLink
    ){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verify your SettleLater Account");

        message.setText(
                "Click This link to verify: "+
                        verificationLink
        );

        javaMailSender.send(message);
    }
}

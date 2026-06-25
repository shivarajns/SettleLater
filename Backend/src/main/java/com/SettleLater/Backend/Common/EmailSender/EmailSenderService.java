package com.SettleLater.Backend.Common.EmailSender;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Async
    public void passwordResetMail(
            String to,
            String resetLink
    ){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject("Reset your Password");
        mail.setText("Click this link to reset your Password: "+resetLink);
        javaMailSender.send(mail);
    }

    @Async
    public void sendSuccessMail(String To,String Subject, String Message){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(To);
        mail.setSubject(Subject);
        mail.setFrom("SettleLater");
        mail.setText(Message);

        javaMailSender.send(mail);
    }
}

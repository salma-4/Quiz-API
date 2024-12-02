package org.app.quizapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {
    private final JavaMailSender mailSender;
    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("salmasobhy456@gmail.com");
        //message.setTo(toEmail);
        message.setSubject("Verification Code");

        message.setText("Your Verification code is: " + otp+" \nIt last for an hour");

        mailSender.send(message);
    }
}

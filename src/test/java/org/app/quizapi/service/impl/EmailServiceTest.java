package org.app.quizapi.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private EmailServiceImpl emailService;

    private  String toEmail;
    private  String otp;
    @BeforeEach
    void setUp() {
       toEmail= "salmasobhy456@gmail.com";
       otp = "123456";
    }

    @Test
    void sendOtpEmail() {
        //Arrange
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        // Act
        emailService.sendOtpEmail(toEmail, otp);

        // Assert
        Mockito.verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage capturedMessage = messageCaptor.getValue();

        assertThat(capturedMessage.getTo()).contains(toEmail);
        assertThat(capturedMessage.getSubject()).isEqualTo("Verification Code");
        assertThat(capturedMessage.getText()).isEqualTo("Your Verification code is: " + otp + " \nIt last for an hour");
    }
    }

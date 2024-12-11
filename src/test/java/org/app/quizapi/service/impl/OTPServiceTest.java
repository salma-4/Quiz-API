package org.app.quizapi.service.impl;

import org.app.quizapi.dto.otp.OTPResponseDTO;
import org.app.quizapi.entity.OTP;
import org.app.quizapi.entity.User;
import org.app.quizapi.mapper.otp.OTPMapper;
import org.app.quizapi.repository.OTPRepo;
import org.app.quizapi.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class OTPServiceTest {
    @InjectMocks
    OTPServiceImpl otpService;
    @Mock
    private OTPMapper otpMapper;
    @Mock
    private OTPRepo otpRepo;
    private User user;
    private String otpCode;
    private OTP mockOtp;

    @BeforeEach
    void setUp() {
        user = User.builder().firstName("salma").lastName("sobhy")
                .email("salma@gmail.com").password("123456")
                .build();

        otpCode = "123456";
        mockOtp = OTP.builder()
                .otp(otpCode)
                .expirationTime(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
    }

    @Test
    void generateOTPTest_returnOTPResponseDTO() {
        //Arrange
        Mockito.when(otpRepo.save(Mockito.any())).thenReturn(mockOtp);
        OTPResponseDTO mockOTOResponse = new OTPResponseDTO();
        Mockito.when(otpMapper.toDTO(Mockito.any(OTP.class))).thenReturn(mockOTOResponse);
        System.out.println(mockOTOResponse);
        //Act
        OTPResponseDTO result = otpService.generateOTP(user);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(mockOTOResponse);
        Mockito.verify(otpRepo).save(Mockito.any(OTP.class));
    }

    @Test
    void validateOtp_validOTP_returnsTrue() {
        //Arrange
        Mockito.when(otpRepo.findByOtpAndUser(otpCode, user)).thenReturn(Optional.of(mockOtp));
        //Act
        boolean result = otpService.validateOtp(otpCode, user);
        //Assert
        assertThat(result).isTrue();
        Mockito.verify(otpRepo).findByOtpAndUser(otpCode, user);
        Mockito.verify(otpRepo).delete(mockOtp);
    }

    @Test
    void validateOtp_notValidOtp_returnsFalse() {
        //Arrange
        mockOtp.setExpirationTime(LocalDateTime.now().minusHours(1));
        Mockito.when(otpRepo.findByOtpAndUser(otpCode, user)).thenReturn(Optional.of(mockOtp));

        //Act
        boolean result = otpService.validateOtp(otpCode, user);

        //Assert
        assertThat(result).isFalse();
        Mockito.verify(otpRepo).findByOtpAndUser(otpCode, user);
    }

}
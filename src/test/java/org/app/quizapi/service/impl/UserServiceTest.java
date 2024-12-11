package org.app.quizapi.service.impl;

import org.app.quizapi.dto.otp.OTPRequestDTO;
import org.app.quizapi.dto.otp.OTPResponseDTO;
import org.app.quizapi.entity.User;
import org.app.quizapi.exception.RecordNotFoundException;
import org.app.quizapi.mapper.user.UserMapper;
import org.app.quizapi.repository.UserRepo;
import org.app.quizapi.service.OTPService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepo userRepo;
    @Mock
    private OTPService otpService;
    @Mock
    private EmailServiceImpl emailService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;
    private OTPResponseDTO mockOtpResponse;
    private OTPRequestDTO mockOtpRequest;
    private String email;
    private String otpCode;

    @BeforeEach
    void setUp() {
        mockUser = User.builder().firstName("salma").lastName("sobhy").username("salmaSob7y").password("123456").email("salma@gmail.com").build();
        mockOtpResponse = OTPResponseDTO.builder().otp("123456").email("salma@gmail.com").build();
        mockOtpRequest = OTPRequestDTO.builder().otp("123456").email("salma@gmail.com").newPassword("salma14").build();
        email = mockUser.getEmail();
        otpCode = mockOtpResponse.getOtp();
    }

    @Test
    void forgetPasswordTest_existingEmail_sendsOtpAndReturnsMsg() {
        //Arrange
        Mockito.when(userRepo.getByEmail(email)).thenReturn(Optional.of(mockUser));
        Mockito.when(otpService.generateOTP(mockUser)).thenReturn(mockOtpResponse);

        //Act
        String result = userService.forgetPassword(email);

        //Assert
        assertThat(result).isEqualTo("Check your mail for verification code");
        Mockito.verify(userRepo).getByEmail(email);
        Mockito.verify(emailService).sendOtpEmail(email, otpCode);
        Mockito.verify(otpService).generateOTP(mockUser);
    }

    @Test
    void forgetPasswordTest_nonExistingEmail_throwsException() {
        //Arrange
        Mockito.when(userRepo.getByEmail(email)).thenReturn(Optional.empty());

        //Act & Assert
        assertThatThrownBy(() -> userService.forgetPassword(email))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining(email + " not exist");
    }

    @Test
    void resetPasswordTest_validOtpExistingEmail_changesPasswordAndReturnsMsg() {
        //Arrange
        Mockito.when(userRepo.getByEmail(email)).thenReturn(Optional.of(mockUser));
        Mockito.when(otpService.validateOtp(otpCode, mockUser)).thenReturn(true);
        Mockito.when(passwordEncoder.encode(mockOtpRequest.getNewPassword())).thenReturn("encodedPassword");

        //Act
        String result = userService.resetPassword(mockOtpRequest);

        //Assert
        assertThat(result).isEqualTo("Password changed ");
        assertThat(mockUser.getPassword()).isEqualTo("encodedPassword");
        Mockito.verify(userRepo).getByEmail(email);
        Mockito.verify(otpService).validateOtp(otpCode, mockUser);
        Mockito.verify(passwordEncoder).encode(mockOtpRequest.getNewPassword());
        Mockito.verify(userRepo).save(mockUser);


    }

    @Test
    void resetPasswordTest_inValidOtp_returnsErrorMsg() {
        //Arrange
        Mockito.when(userRepo.getByEmail(email)).thenReturn(Optional.of(mockUser));
        Mockito.when(otpService.validateOtp(otpCode, mockUser)).thenReturn(false);

        //Act
        String result = userService.resetPassword(mockOtpRequest);

        //Assert
        assertThat(result).isEqualTo("something went wrong try again");
        Mockito.verify(userRepo).getByEmail(email);
        Mockito.verify(otpService).validateOtp(otpCode, mockUser);
        Mockito.verifyNoMoreInteractions(userRepo, passwordEncoder);
    }

    @Test
    void resetPasswordTest_nonExistingEmail_throwsException() {
        //Arrange
        Mockito.when(userRepo.getByEmail(email)).thenReturn(Optional.empty());

        //Act & Arrange
        assertThatThrownBy(() -> userService.resetPassword(mockOtpRequest))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining(email + " does not exist");
    }

}
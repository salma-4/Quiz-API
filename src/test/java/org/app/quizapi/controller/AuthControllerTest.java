package org.app.quizapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.app.quizapi.dto.otp.OTPRequestDTO;
import org.app.quizapi.dto.user.AuthResponse;
import org.app.quizapi.dto.user.UserLoginDTO;
import org.app.quizapi.dto.user.UserRegisterDTO;
import org.app.quizapi.repository.UserTokenRepo;
import org.app.quizapi.security.JWTService;
import org.app.quizapi.service.AuthService;
import org.app.quizapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {AuthController.class})
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserTokenRepo userTokenRepo;
    @MockBean
    private JWTService jwtService;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthService authService;

    private AuthResponse mockAuthResponse;
    private UserRegisterDTO mockUserRegisterDto;
    private UserLoginDTO mockUserLoginDto;
    private OTPRequestDTO mockOtpRequestDTO;
    @BeforeEach
    void setUp() {
        mockAuthResponse = AuthResponse.builder().username("salmaSob7y").token("validToken").build();
        mockUserRegisterDto = UserRegisterDTO.builder().firstName("salma").lastName("sobhy").password("12345").email("salma@gmail.com").build();
        mockUserLoginDto = UserLoginDTO.builder().username("salmaSob7y").password("12345").build();
        mockOtpRequestDTO = OTPRequestDTO.builder().newPassword("45678").email("salma@gmail.com").otp("890654").build();
    }

    @Test
    void registerTest_returnsAuthResponse() throws Exception {
        //Arrange
        Mockito.when(authService.register(Mockito.any(UserRegisterDTO.class))).thenReturn(mockAuthResponse);

        //Act & Assert
        mockMvc.perform(
                post("/quizApp/v1/auth/newUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockUserRegisterDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(mockAuthResponse.getUsername()))
                .andExpect(jsonPath("$.token").value(mockAuthResponse.getToken()));
    }

    @Test
    void loginTest_returnsAuthResponse() throws Exception {
        //Arrange
        Mockito.when(authService.login(Mockito.any(UserLoginDTO.class))).thenReturn(mockAuthResponse);

        //Act & Assert
        mockMvc.perform(
                post("/quizApp/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockUserLoginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(mockAuthResponse.getUsername()))
                .andExpect(jsonPath("$.token").value(mockAuthResponse.getToken()));
    }

    @Test
    void forgetPasswordTest_returnsCheckMailMsg() throws Exception {
        //Arrange
        String email = "salma@gmail.com";
        String expectedMsg ="OTP to reset password send to "+email;

        Mockito.when(userService.forgetPassword(email)).thenReturn(expectedMsg);

        //Act & Assert
        mockMvc.perform(
                post("/quizApp/v1/auth/user")
                        .param("email",email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMsg));
    }

    @Test
    void resetPasswordTest_returnsConfirmationMsg() throws Exception {
        //Arrange
        String expectedMsg = "Password changed";
        Mockito.when(userService.resetPassword(Mockito.any(OTPRequestDTO.class))).thenReturn(expectedMsg);

        //Act & Assert
        mockMvc.perform(
                post("/quizApp/v1/auth/user/newPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockOtpRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMsg));
    }

    @Test
    void regenerateOtpTest_returnsCheckMailMsg() throws Exception{
        //Arrange
        String email = "salma@gmail.com";
        String expectedMsg ="OTP to reset password send to "+email;
        Mockito.when(userService.regenerateOtp(email)).thenReturn(expectedMsg);

        //Act & Assert
        mockMvc.perform(
                post("/quizApp/v1/auth/user/otp")
                        .param("email",email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMsg));
    }
}
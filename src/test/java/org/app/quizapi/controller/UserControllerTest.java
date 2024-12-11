package org.app.quizapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.app.quizapi.repository.UserTokenRepo;
import org.app.quizapi.security.JWTService;
import org.app.quizapi.service.AuthService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {UserController.class})
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    AuthService authService;
    @MockBean
    private UserTokenRepo userTokenRepo;
    @MockBean
    JWTService jwtService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void logoutTest_returnsSuccessMsg() throws Exception{
      //Arrange
        String expectedMessage = "Logged out successfully";
        Mockito.when(authService.logout(Mockito.any(HttpServletRequest.class))).thenReturn(expectedMessage);
      //Act & Assert
        mockMvc.perform(
                post("/quizApp/v1/user/logout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }
}
package org.app.quizapi.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.app.quizapi.dto.user.AuthResponse;
import org.app.quizapi.dto.user.UserLoginDTO;
import org.app.quizapi.dto.user.UserRegisterDTO;
import org.app.quizapi.entity.User;
import org.app.quizapi.entity.UserToken;
import org.app.quizapi.exception.ConflictException;
import org.app.quizapi.exception.RecordNotFoundException;
import org.app.quizapi.mapper.user.UserMapper;
import org.app.quizapi.repository.UserRepo;
import org.app.quizapi.repository.UserTokenRepo;
import org.app.quizapi.security.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JWTService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserTokenRepo userTokenRepo;
    @InjectMocks
    private AuthServiceImpl authService;

    private User mockUser;
    private UserRegisterDTO mockUserRegisterDto;
    private UserLoginDTO mockUserLoginDTO;
    private AuthResponse mockAuthResponse;
    private UserToken mockUserToken;
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockUserRegisterDto = UserRegisterDTO.builder().firstName("salma").lastName("sobhy").username("salmaSob7y").email("salma@gmail.com").password("123456").build();
        mockUser = User.builder().firstName("salma").lastName("sobhy").username("salmaSob7y").email("salma@gmail.com").password("123456").build();
        mockUserToken = UserToken.builder().token("mockedToken").createdAt(LocalDateTime.now()).expiresAt(LocalDateTime.now().plusHours(1)).user(mockUser).build();
        mockAuthResponse = AuthResponse.builder().token("mockedToken").username("salmaSob7y").build();
        mockUserLoginDTO = UserLoginDTO.builder().username("salmaSob7y").password("12345").build();

        mockRequest = Mockito.mock(HttpServletRequest.class);

    }

    @Test
    void registerTest_successfulRegistration_returnsAuthResponse() {
        //Arrange
        Mockito.when(userMapper.toRegisterEntity(mockUserRegisterDto)).thenReturn(mockUser);
        Mockito.when(userRepo.getByUsername(mockUserRegisterDto.getUsername())).thenReturn(Optional.empty());
        Mockito.when(userRepo.getByEmail(mockUserRegisterDto.getEmail())).thenReturn(Optional.empty());
        Mockito.when(jwtService.generateToken(mockUser)).thenReturn("mockedToken");
        Mockito.when(userMapper.toEntityToken(mockUser, "mockedToken")).thenReturn(mockUserToken);

        //Act
        AuthResponse response = authService.register(mockUserRegisterDto);

        //Assert
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo(mockAuthResponse.getUsername());
        Mockito.verify(userRepo).save(mockUser);
        Mockito.verify(userTokenRepo).save(mockUserToken);
    }

    @Test
    void RegisterTest_usernameConflict_throwsException() {
        //Arrange
        String username = mockUser.getUsername();
        Mockito.when(userRepo.getByUsername(username)).thenReturn(Optional.of(mockUser));

        //Act & Assert
        assertThatThrownBy(() -> authService.register(mockUserRegisterDto))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Username already exists");
    }

    @Test
    void RegisterTest_emailConflict_throwsException() {
        //Arrange
        String email = mockUser.getEmail();
        Mockito.when(userRepo.getByEmail(email)).thenReturn(Optional.of(mockUser));

        //Act & Assert
        assertThatThrownBy(() -> authService.register(mockUserRegisterDto))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Email already exists");
    }

    @Test
    void loginTest_successfulLogin_returnsAuthResponse() {
        //Arrange
        String username = mockUser.getUsername();
        Mockito.when(userRepo.getByUsername(username)).thenReturn(Optional.of(mockUser));
        Mockito.when(jwtService.generateToken(mockUser)).thenReturn("mockedToken");
        Mockito.when(userTokenRepo.findByUser(mockUser)).thenReturn(Optional.empty());
        Mockito.when(userMapper.toEntityToken(mockUser, "mockedToken")).thenReturn(mockUserToken);

        //Act
        AuthResponse response = authService.login(mockUserLoginDTO);

        //Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(mockAuthResponse.getToken());
        Mockito.verify(authenticationManager).authenticate(Mockito.any());
        Mockito.verify(userTokenRepo).save(mockUserToken);

    }

    @Test
    void loginTest_invalidUsername_throwsException() {
        //Arrange
        String username = mockUser.getUsername();
        Mockito.when(userRepo.getByUsername(username)).thenReturn(Optional.empty());

        //Act & Assert
        assertThatThrownBy(() -> authService.login(mockUserLoginDTO))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("No user with username " + username);

    }

    @Test
    void logout_validToken_successfulLogout() {
        //Arrange
        String token = "validToken";
        Long userId = 1L;
        mockUser.setId(userId);

        Mockito.when(jwtService.extractToken(mockRequest)).thenReturn(token);
        Mockito.when(userTokenRepo.findByToken(token)).thenReturn(Optional.of(mockUserToken));
        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));

        //Act
        String result = authService.logout(mockRequest);

        //Assert
        assertThat(result).isEqualTo("User " + mockUser.getUsername() + " has been logged out successfully.");
        Mockito.verify(userTokenRepo).delete(mockUserToken);

    }

    @Test
    void logoutTest_invalidToken_throwsException() {
        //Arrange
        String token = "inValidToken";
        Mockito.when(jwtService.extractToken(mockRequest)).thenReturn(token);
        Mockito.when(userTokenRepo.findByToken(token)).thenReturn(Optional.empty());

        //Act & Assert
        assertThatThrownBy(() -> authService.logout(mockRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Not valid token");
        Mockito.verify(userTokenRepo, Mockito.never()).delete(Mockito.any());

    }
}
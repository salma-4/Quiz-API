package org.app.quizapi.service;


import jakarta.servlet.http.HttpServletRequest;
import org.app.quizapi.dto.user.AuthResponse;
import org.app.quizapi.dto.user.UserLoginDTO;
import org.app.quizapi.dto.user.UserRegisterDTO;


public interface AuthService {

AuthResponse register(UserRegisterDTO user);
AuthResponse login (UserLoginDTO user);
String logout(HttpServletRequest request);

}

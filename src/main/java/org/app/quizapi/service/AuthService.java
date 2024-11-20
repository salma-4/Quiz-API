package org.app.quizapi.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.quizapi.dto.AuthResponse;
import org.app.quizapi.dto.UserRegisterDTO;
import org.app.quizapi.repository.UserRepo;
import org.springframework.stereotype.Service;


public interface AuthService {

AuthResponse register(UserRegisterDTO user);


}

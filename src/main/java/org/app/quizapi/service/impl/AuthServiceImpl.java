package org.app.quizapi.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.quizapi.dto.AuthResponse;
import org.app.quizapi.dto.UserRegisterDTO;
import org.app.quizapi.entity.User;
import org.app.quizapi.exception.ConflictException;
import org.app.quizapi.mapper.UserMapper;
import org.app.quizapi.repository.UserRepo;
import org.app.quizapi.security.JWTService;
import org.app.quizapi.service.AuthService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final JWTService jwtService;
    @Override
    public AuthResponse register(UserRegisterDTO user) {
        User newUser = userMapper.toRegisterEntity(user);
        if (userRepo.getUserByUsername(user.getUsername()).isPresent()) {
            throw new ConflictException("Username already exists");
        }
        if (userRepo.getUserByEmail(user.getEmail()).isPresent()) {
            throw new ConflictException("Email already exists");
        }
        userRepo.save(newUser);
        String token = jwtService.generateToken(newUser);
        return new AuthResponse(token, newUser.getUsername());
    }

}

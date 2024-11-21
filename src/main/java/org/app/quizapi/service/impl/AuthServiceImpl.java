package org.app.quizapi.service.impl;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.quizapi.dto.AuthResponse;
import org.app.quizapi.dto.UserLoginDTO;
import org.app.quizapi.dto.UserRegisterDTO;
import org.app.quizapi.entity.User;
import org.app.quizapi.entity.UserToken;
import org.app.quizapi.exception.ConflictException;
import org.app.quizapi.exception.RecordNotFoundException;
import org.app.quizapi.mapper.UserMapper;
import org.app.quizapi.repository.UserRepo;
import org.app.quizapi.repository.UserTokenRepo;
import org.app.quizapi.security.JWTService;
import org.app.quizapi.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserTokenRepo userTokenRepo;
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
        UserToken userToken =userMapper.toEntityToken(newUser,token);
        userTokenRepo.save(userToken);
        return new AuthResponse(token, newUser.getUsername());
    }


    @Override
    public AuthResponse login(UserLoginDTO user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        User existingUser = userRepo.getUserByUsername(user.getUsername())
                .orElseThrow(() -> new RecordNotFoundException("No user with username " + user.getUsername()));

        String newToken = jwtService.generateToken(existingUser);

        Optional<UserToken> existingToken = userTokenRepo.findByUser(existingUser);
        if(existingToken.isPresent()){
            //update it
            UserToken userToken = existingToken.get();
            userToken.setToken(newToken);
            userToken.setCreatedAt(LocalDateTime.now());
            userToken.setExpiresAt(LocalDateTime.now().plusHours(1));
            userTokenRepo.save(userToken);
        }
        else{
            // create it
            UserToken userToken =userMapper.toEntityToken(existingUser,newToken);
            userTokenRepo.save(userToken);
        }
        return new AuthResponse(newToken, existingUser.getUsername());
    }

    @Override
    public String logout(HttpServletRequest request)  {
        String token = jwtService.extractToken(request);
        UserToken userToken = userTokenRepo.findByToken(token)
                .orElseThrow(()->new RuntimeException("Not valid token"));
        User user = userRepo.findById(userToken.getUser().getId())
                .orElseThrow(()->new ConflictException("Not the user"));
        userTokenRepo.delete(userToken);
        return "User " + user.getUsername() + " has been logged out successfully.";
    }

}

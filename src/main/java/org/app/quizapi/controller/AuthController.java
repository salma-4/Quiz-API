package org.app.quizapi.controller;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.AuthResponse;
import org.app.quizapi.dto.UserLoginDTO;
import org.app.quizapi.dto.UserRegisterDTO;
import org.app.quizapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quizApp/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/newUser")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegisterDTO newUser){
        AuthResponse response = authService.register(newUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginDTO userRequest){
        AuthResponse response= authService.login(userRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

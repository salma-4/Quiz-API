package org.app.quizapi.controller;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.otp.OTPRequestDTO;
import org.app.quizapi.dto.user.AuthResponse;
import org.app.quizapi.dto.user.UserLoginDTO;
import org.app.quizapi.dto.user.UserRegisterDTO;
import org.app.quizapi.service.AuthService;
import org.app.quizapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quizApp/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

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
    @PostMapping("/user")
    public ResponseEntity<String> forgetPassword(@RequestParam String email){
        String msg = userService.forgetPassword(email);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }
    @PostMapping("/user/newPassword")
    public ResponseEntity<String> resetPassword(@RequestBody OTPRequestDTO request){
        String msg = userService.resetPassword(request);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }
    @PostMapping("/user/otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email){
        String msg = userService.regenerateOtp(email);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }}

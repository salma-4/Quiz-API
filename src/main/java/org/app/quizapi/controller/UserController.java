package org.app.quizapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.app.quizapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quizApp/v1/user")
public class UserController {
private final AuthService authService;
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
       String msg = authService.logout(request);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}

package org.app.quizapi.mapper.impl;


import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.UserRegisterDTO;
import org.app.quizapi.entity.User;
import org.app.quizapi.entity.UserToken;
import org.app.quizapi.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User toRegisterEntity(UserRegisterDTO user) {
        return User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
    }

    @Override
    public UserToken toEntityToken(User user, String token) {
        return UserToken.builder()
                .token(token)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(1))
                .build();
    }
}

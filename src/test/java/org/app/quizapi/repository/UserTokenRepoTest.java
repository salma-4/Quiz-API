package org.app.quizapi.repository;

import org.app.quizapi.entity.User;
import org.app.quizapi.entity.UserToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserTokenRepoTest {

    @Autowired
    private UserTokenRepo userTokenRepo;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void findByToken_existingToken_returnsUserToken() {
        // Arrange
        User user = User.builder()
                .username("salma4")
                .password("123445")
                .email("salma@gmail.com")
                .firstName("salma")
                .lastName("sobhy")
                .build();
        userRepo.save(user);

        UserToken userToken = UserToken.builder()
                .token("validToken123")
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
        userTokenRepo.save(userToken);

        // Act
        Optional<UserToken> token = userTokenRepo.findByToken("validToken123");

        // Assert
        assertTrue(token.isPresent());
        assertEquals("validToken123", token.get().getToken());
    }

    @Test
    public void findByToken_nonExistingToken_returnsEmpty() {
        // Act
        Optional<UserToken> token = userTokenRepo.findByToken("invalidToken");

        // Assert
        assertTrue(token.isEmpty());
    }

    @Test
    public void findByUser_existingUser_returnsUserToken() {
        // Arrange
        User user = User.builder()
                .username("salma4")
                .password("123445")
                .email("salma@gmail.com")
                .firstName("salma")
                .lastName("sobhy")
                .build();
        userRepo.save(user);

        UserToken userToken = UserToken.builder()
                .token("validToken123")
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(1))
                .build();
        userTokenRepo.save(userToken);

        // Act
        Optional<UserToken> token = userTokenRepo.findByUser(user);

        // Assert
        assertTrue(token.isPresent());
        assertEquals(user, token.get().getUser());
    }

}

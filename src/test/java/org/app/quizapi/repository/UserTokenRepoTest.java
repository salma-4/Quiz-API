package org.app.quizapi.repository;

import org.app.quizapi.entity.User;
import org.app.quizapi.entity.UserToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class UserTokenRepoTest {

    @Autowired
    private UserTokenRepo userTokenRepo;

    @Autowired
    private UserRepo userRepo;

    private User user;
    private UserToken userToken;

    @BeforeEach
    public void init(){
        user = User.builder()
                .username("salma4")
                .password("123445")
                .email("salma@gmail.com")
                .firstName("salma")
                .lastName("sobhy")
                .build();

        userToken = UserToken.builder()
                .token("validToken123")
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
    }
    @Test
    public void findByToken_existingToken_returnsUserToken() {
        // Arrange
        userRepo.save(user);

        userTokenRepo.save(userToken);

        // Act
        Optional<UserToken> token = userTokenRepo.findByToken("validToken123");

        // Assert
        assertThat(token.isPresent()).isTrue();
                           // correctly retrieve all fields
        assertThat(token.get()).usingRecursiveComparison().isEqualTo(userToken);
    }

    @Test
    public void findByToken_nonExistingToken_returnsEmpty() {
        // Act
        Optional<UserToken> token = userTokenRepo.findByToken("invalidToken");

        // Assert
        assertThat(token.isEmpty());
    }

    @Test
    public void findByUser_existingUser_returnsUserToken() {
        // Arrange
        userRepo.save(user);
        userTokenRepo.save(userToken);

        // Act
        Optional<UserToken> token = userTokenRepo.findByUser(user);

        // Assert
        assertThat(token.isPresent()).isTrue();
        assertThat(user).isEqualTo(token.get().getUser());
    }

}

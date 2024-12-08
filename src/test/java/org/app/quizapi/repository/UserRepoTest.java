package org.app.quizapi.repository;

import org.app.quizapi.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@DataJpaTest
class UserRepoTest {
    @Autowired
   private UserRepo userRepo;
    private User user;
    @BeforeEach
    public void init(){
        user = User.builder()
                .username("salma4")
                .password("123445")
                .email("salma@gmail.com")
                .firstName("salma")
                .lastName("sobhy")
                .build();
    }


    @Test
    public void getByUsername_existingUser_returnUser(){
        //Arrange
        userRepo.save(user);

        //Act
        Optional<User> user = userRepo.getByUsername("salma4");

        //assert
        assertThat(user.isPresent());
        assertThat("salma4").isEqualTo(user.get().getUsername());
    }

    @Test
    public void getByEmail_existingEmail_returnUser(){
        //Arrange
        userRepo.save(user);

        //Act
        Optional<User> user = userRepo.getByEmail("salma@gmail.com");

        //assert
        assertThat(user.isPresent());
        assertThat("salma@gmail.com").isEqualTo(user.get().getEmail());
    }

    @Test
    public void saveUser_persistsAllFieldsCorrectly() {
        // Arrange & Act
        User savedUser = userRepo.save(user);

        // Assert
        assertThat(savedUser.getUsername()).isEqualTo("salma4");
        assertThat(savedUser.getPassword()).isEqualTo("123445");
        assertThat(savedUser.getEmail()).isEqualTo("salma@gmail.com");
        assertThat(savedUser.getFirstName()).isEqualTo("salma");
        assertThat(savedUser.getLastName()).isEqualTo("sobhy");
    }


}
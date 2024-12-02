package org.app.quizapi.repository;

import org.app.quizapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepoTest {
    @Autowired
    UserRepo userRepo;


    @Test
    public void getByUsername_existingUser_returnUser(){
        //Arrange
        User user1 = User.builder()
                .username("salma4")
                .password("123445")
                .email("salma@gmail.com")
                .firstName("salma")
                .lastName("sobhy")
                .build();

        userRepo.save(user1);

        //Act
        Optional<User> user = userRepo.getByUsername("salma4");

        //assert
        assertTrue(user.isPresent());
        assertEquals("salma4",user.get().getUsername());
    }

    @Test
    public void getByEmail_existingEmail_returnUser(){
        //Arrange
        User user1 = User.builder()
                .username("salma4")
                .password("123445")
                .email("salma@gmail.com")
                .firstName("salma")
                .lastName("sobhy")
                .build();

        userRepo.save(user1);

        //Act
        Optional<User> user = userRepo.getByEmail("salma@gmail.com");

        //assert
        assertTrue(user.isPresent());
        assertEquals("salma@gmail.com",user.get().getEmail());
    }

    @Test
    public void saveUser_persistsAllFieldsCorrectly() {
        // Arrange
        User user = User.builder()
                .username("salma4")
                .password("123445")
                .email("salma@gmail.com")
                .firstName("salma")
                .lastName("sobhy")
                .build();

        // Act
        User savedUser = userRepo.save(user);

        // Assert
        assertEquals("salma4", savedUser.getUsername());
        assertEquals("123445", savedUser.getPassword());
        assertEquals("salma@gmail.com", savedUser.getEmail());
        assertEquals("salma", savedUser.getFirstName());
        assertEquals("sobhy", savedUser.getLastName());
    }


}
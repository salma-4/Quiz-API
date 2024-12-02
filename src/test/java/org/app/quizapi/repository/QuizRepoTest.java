package org.app.quizapi.repository;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.entity.Question;
import org.app.quizapi.entity.Quiz;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class QuizRepoTest {

    @Autowired
    private  QuizRepo quizRepo;

    @Test
    public void findByCategory_withExistingCategory_returnQuizzes(){

        // Arrange
        Quiz quiz1 = Quiz.builder()
                .type("Math")
                .build();

        quizRepo.save(quiz1);

        //Act
        Optional<Quiz> quiz = quizRepo.findQuizByType("Math");

        // Assert
        assertTrue(quiz.isPresent());
        assertEquals("Math", quiz.get().getType());
        System.out.println(quiz.get().getQuestions());
    }

    @Test
    public void findByCategory_WithNonExistingCategory_returnEmpty(){

        // Arrange
        Quiz quiz1 = Quiz.builder()
                .type("Math")
                .build();

        quizRepo.save(quiz1);

        // Act
        Optional<Quiz> quiz = quizRepo.findQuizByType("Science");

        // Assert
        assertFalse(quiz.isPresent());

    }
}
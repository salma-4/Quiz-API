package org.app.quizapi.repository;

import org.app.quizapi.entity.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class QuizRepoTest {

    @Autowired
    private  QuizRepo quizRepo;
    private Quiz quiz;
    @BeforeEach
    public void init(){
        quiz= Quiz.builder()
                .type("Math")
                .build();
    }

    @Test
    public void findByCategory_withExistingCategory_returnQuizzes(){

        // Arrange
        quizRepo.save(quiz);

        //Act
        Optional<Quiz> quiz = quizRepo.findQuizByType("Math");

        // Assert
        assertThat(quiz.isPresent());
        assertThat("Math").isEqualTo( quiz.get().getType());
    }

    @Test
    public void findByCategory_WithNonExistingCategory_returnEmpty(){

        // Arrange
        quizRepo.save(quiz);

        // Act
        Optional<Quiz> quiz = quizRepo.findQuizByType("Science");

        // Assert
        assertThat(quiz.isPresent()).isFalse();

    }
}
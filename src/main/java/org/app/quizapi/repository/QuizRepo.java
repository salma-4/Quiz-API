package org.app.quizapi.repository;

import org.app.quizapi.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepo extends JpaRepository<Quiz,Long> {
    Optional<Quiz> findQuizByType(String type);

}

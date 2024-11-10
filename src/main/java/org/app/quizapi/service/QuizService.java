package org.app.quizapi.service;

import org.app.quizapi.dto.QuizDto;
import org.app.quizapi.dto.QuizResponseDto;

import java.util.List;

public interface QuizService {

    String createQuiz (QuizDto quizDto);
    List<QuizResponseDto> getAllQuizCategories();
    String deletQuiz(Long quizId);
    QuizDto getQuizByType(String type);

}

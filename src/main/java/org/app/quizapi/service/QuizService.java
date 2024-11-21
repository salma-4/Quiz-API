package org.app.quizapi.service;

import org.app.quizapi.dto.quiz.QuizDTO;
import org.app.quizapi.dto.quiz.QuizRequestDto;
import org.app.quizapi.dto.quiz.QuizResponseDto;

import java.util.List;

public interface QuizService {

    String createQuiz (QuizRequestDto quizRequestDto);
    List<QuizResponseDto> getAllQuizCategories();
    String deletQuiz(Long quizId);
    QuizRequestDto getQuizByType(String type);

    QuizDTO getQuizById(Long id);
    String updateQuiz(QuizRequestDto newQuiz, Long quizId);

}

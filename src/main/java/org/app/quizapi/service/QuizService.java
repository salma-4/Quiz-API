package org.app.quizapi.service;

import org.app.quizapi.dto.QuizDto;
import org.app.quizapi.dto.QuizQuestionsDto;

import java.util.List;

public interface QuizService {

    String createQuiz (QuizDto questionDto);
    String updateQuiz(QuizDto quizDto);
    String deletQuiz(Long quizId);
    List<QuizDto> getAllQuiz();
    QuizQuestionsDto getQuizByType(String type);

}

package org.app.quizapi.mapper;

import org.app.quizapi.dto.QuestionsDto;
import org.app.quizapi.dto.QuizDto;
import org.app.quizapi.dto.QuizResponseDto;
import org.app.quizapi.entity.Question;
import org.app.quizapi.entity.Quiz;

public interface QuizMapper {
    Quiz toEntity(QuizDto quizDto);
    QuizDto toDTO(Quiz quiz);
    QuizResponseDto toResponseDTO(Quiz quiz);
}

package org.app.quizapi.mapper.quiz;

import org.app.quizapi.dto.quiz.QuizDTO;
import org.app.quizapi.dto.quiz.QuizRequestDto;
import org.app.quizapi.dto.quiz.QuizResponseDto;
import org.app.quizapi.entity.Quiz;

public interface QuizMapper {
    Quiz toEntity(QuizRequestDto quizRequestDto);
    QuizRequestDto toDTO(Quiz quiz);
    QuizResponseDto toResponseDTO(Quiz quiz);
    QuizDTO toResponse(Quiz quiz);
}

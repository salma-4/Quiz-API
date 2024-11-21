package org.app.quizapi.mapper.question;

import org.app.quizapi.dto.question.QuestionResponseDTO;
import org.app.quizapi.dto.question.QuestionsDTO;
import org.app.quizapi.entity.Question;

public interface QuestionMapper {
    QuestionsDTO toDTO(Question question);
    Question toEntity(QuestionsDTO questionsDto);
    QuestionResponseDTO toResponseDTO(Question question);
}

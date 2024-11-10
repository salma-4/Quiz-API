package org.app.quizapi.mapper;

import org.app.quizapi.dto.QuestionsDto;
import org.app.quizapi.entity.Question;

public interface QuestionMapper {
    QuestionsDto toDTO(Question question);
    Question toEntity(QuestionsDto questionsDto);
}

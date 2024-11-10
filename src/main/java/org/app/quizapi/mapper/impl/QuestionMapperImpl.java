package org.app.quizapi.mapper.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.QuestionsDto;
import org.app.quizapi.entity.Question;
import org.app.quizapi.mapper.QuestionMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionMapperImpl implements QuestionMapper {
    @Override
    public QuestionsDto toDTO(Question question) {
        return QuestionsDto.builder()
                .questionTitle(question.getQuestionTitle())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .answer(question.getAnswer())
                .degree(question.getDegree())
                .build();

    }

    @Override
    public Question toEntity(QuestionsDto questionsDto) {
        return Question.builder()
                .questionTitle(questionsDto.getQuestionTitle())
                .optionA(questionsDto.getOptionA())
                .optionB(questionsDto.getOptionB())
                .optionC(questionsDto.getOptionC())
                .optionD(questionsDto.getOptionD())
                .answer(questionsDto.getAnswer())
                .degree(questionsDto.getDegree())
                .build();
    }
}

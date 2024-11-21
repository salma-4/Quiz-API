package org.app.quizapi.mapper.question;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.question.QuestionResponseDTO;
import org.app.quizapi.dto.question.QuestionsDTO;
import org.app.quizapi.entity.Question;
import org.app.quizapi.mapper.question.QuestionMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionMapperImpl implements QuestionMapper {
    @Override
    public QuestionsDTO toDTO(Question question) {
        return QuestionsDTO.builder()
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
    public Question toEntity(QuestionsDTO questionsDto) {
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

    @Override
    public QuestionResponseDTO toResponseDTO(Question question) {
        return QuestionResponseDTO.builder()
                .id(question.getId())
                .questionTitle(question.getQuestionTitle())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .degree(question.getDegree())
                .build();
    }
}

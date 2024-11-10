package org.app.quizapi.mapper.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.QuestionsDto;
import org.app.quizapi.dto.QuizDto;
import org.app.quizapi.dto.QuizResponseDto;
import org.app.quizapi.entity.Question;
import org.app.quizapi.entity.Quiz;
import org.app.quizapi.mapper.QuestionMapper;
import org.app.quizapi.mapper.QuizMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuizMapperImpl implements QuizMapper {

 private final QuestionMapper questionMapper;

    @Override
    public Quiz toEntity(QuizDto quizDto) {

        List<Question> questions = quizDto.getQuestions()
                .stream()
                .map(questionMapper::toEntity)
                .collect(Collectors.toList());

        return   Quiz.builder()
                .type(quizDto.getType())
                .questions(questions)
                .build();


    }

    @Override
    public QuizDto toDTO(Quiz quiz) {
        List<QuestionsDto> questionsDto= quiz.getQuestions()
                .stream()
                .map(questionMapper ::toDTO)
                .collect(Collectors.toList());
        return QuizDto.builder()
                .type(quiz.getType())
                .questions(questionsDto)
                .build();
    }

    @Override
    public QuizResponseDto toResponseDTO(Quiz quiz) {
        return QuizResponseDto.builder()
                .id(quiz.getId())
                .category(quiz.getType())
                .build();
    }
}

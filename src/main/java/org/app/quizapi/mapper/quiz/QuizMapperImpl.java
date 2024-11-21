package org.app.quizapi.mapper.quiz;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.question.QuestionResponseDTO;
import org.app.quizapi.dto.question.QuestionsDTO;
import org.app.quizapi.dto.quiz.QuizDTO;
import org.app.quizapi.dto.quiz.QuizRequestDto;
import org.app.quizapi.dto.quiz.QuizResponseDto;
import org.app.quizapi.entity.Question;
import org.app.quizapi.entity.Quiz;
import org.app.quizapi.mapper.question.QuestionMapper;
import org.app.quizapi.mapper.quiz.QuizMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuizMapperImpl implements QuizMapper {

 private final QuestionMapper questionMapper;

    @Override
    public Quiz toEntity(QuizRequestDto quizRequestDto) {

        List<Question> questions = quizRequestDto.getQuestions()
                .stream()
                .map(questionMapper::toEntity)
                .collect(Collectors.toList());

        return   Quiz.builder()
                .type(quizRequestDto.getType())
                .questions(questions)
                .build();


    }

    @Override
    public QuizRequestDto toDTO(Quiz quiz) {
        List<QuestionsDTO> questionsDto= quiz.getQuestions()
                .stream()
                .map(questionMapper ::toDTO)
                .collect(Collectors.toList());
        return QuizRequestDto.builder()
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

    @Override
    public QuizDTO toResponse(Quiz quiz) {
        List<QuestionResponseDTO> questionResponseList= quiz.getQuestions()
                .stream().map(questionMapper ::toResponseDTO)
                .collect(Collectors.toList());
        return QuizDTO.builder()
                .id(quiz.getId())
                .category(quiz.getType())
                .questionResponse(questionResponseList)
                .build();
    }
}

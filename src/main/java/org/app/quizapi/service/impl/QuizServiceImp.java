package org.app.quizapi.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.QuizDto;
import org.app.quizapi.dto.QuestionsDto;
import org.app.quizapi.dto.QuizResponseDto;
import org.app.quizapi.entity.Quiz;
import org.app.quizapi.exception.RecordNotFoundException;
import org.app.quizapi.mapper.QuizMapper;
import org.app.quizapi.repository.QuizRepo;
import org.app.quizapi.service.QuizService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImp implements QuizService {
    private final QuizRepo quizRepo;
    private final QuizMapper quizMapper;
    @Override
    public String createQuiz(QuizDto quizDto) {
        Quiz quiz = quizMapper.toEntity(quizDto);
        quizRepo.save(quiz);
        return "Quiz added successfully";
    }

    @Override
    public List<QuizResponseDto> getAllQuizCategories() {
        List<Quiz> quizzes = quizRepo.findAll();
        if(quizzes.isEmpty())
            throw new RecordNotFoundException("No categories Added");
          return quizzes
                .stream()
                .map(quizMapper ::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String deletQuiz(Long quizId) {
        Quiz quiz = quizRepo.getById(quizId);
        quizRepo.delete(quiz);
        return "Quiz deleted successfully";
    }


    @Override
    public QuizDto getQuizByType(String type) {
       Quiz quiz= quizRepo.findQuizByType(type)
               .orElseThrow(()-> new RuntimeException("not found exception"));
       return quizMapper.toDTO(quiz);
    }
}

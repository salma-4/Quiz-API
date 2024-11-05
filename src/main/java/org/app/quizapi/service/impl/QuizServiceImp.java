package org.app.quizapi.service.impl;

import org.app.quizapi.dto.QuizDto;
import org.app.quizapi.dto.QuizQuestionsDto;
import org.app.quizapi.service.QuizService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImp implements QuizService {
    @Override
    public String createQuiz(QuizDto questionDto) {
        return null;
    }

    @Override
    public String updateQuiz(QuizDto quizDto) {
        return null;
    }

    @Override
    public String deletQuiz(Long quizId) {
        return null;
    }

    @Override
    public List<QuizDto> getAllQuiz() {
        return null;
    }

    @Override
    public QuizQuestionsDto getQuizByType(String type) {
        return null;
    }
}

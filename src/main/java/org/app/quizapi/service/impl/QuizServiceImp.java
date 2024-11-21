package org.app.quizapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.quiz.QuizDTO;
import org.app.quizapi.dto.quiz.QuizRequestDto;
import org.app.quizapi.dto.quiz.QuizResponseDto;
import org.app.quizapi.entity.Question;
import org.app.quizapi.entity.Quiz;
import org.app.quizapi.exception.RecordNotFoundException;
import org.app.quizapi.mapper.question.QuestionMapper;
import org.app.quizapi.mapper.quiz.QuizMapper;
import org.app.quizapi.repository.QuestionRepo;
import org.app.quizapi.repository.QuizRepo;
import org.app.quizapi.service.QuizService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImp implements QuizService {

    private final QuizRepo quizRepo;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;
    private final QuestionRepo questionRepo;
    @Override
    public String createQuiz(QuizRequestDto quizRequestDto) {
        String category = quizRequestDto.getType();

        Optional<Quiz> existingQuizOptional = quizRepo.findQuizByType(category);

        if (existingQuizOptional.isPresent()) {
            Quiz existingQuiz = existingQuizOptional.get();
            List<Question> questions = quizRequestDto.getQuestions().stream()
                    .map(questionMapper::toEntity)
                    .peek(question -> question.setQuiz(existingQuiz))
                    .collect(Collectors.toList());

            questionRepo.saveAll(questions);

            return "Quiz updated with new questions successfully";
        } else {
            Quiz newQuiz = quizMapper.toEntity(quizRequestDto);
            List<Question> questions = quizRequestDto.getQuestions().stream()
                    .map(questionMapper::toEntity)
                    .peek(question -> question.setQuiz(newQuiz)) // Associate with the new quiz
                    .collect(Collectors.toList());

            newQuiz.setQuestions(questions);
            System.out.println(newQuiz);
            quizRepo.save(newQuiz);

            return "New quiz created successfully";
        }
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
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(()-> new RecordNotFoundException("Quiz not found with ID: " + quizId));
        quizRepo.delete(quiz);
        return "Quiz deleted successfully";
    }

    @Override
    public QuizRequestDto getQuizByType(String type) {
       Quiz quiz= quizRepo.findQuizByType(type)
               .orElseThrow(()-> new RecordNotFoundException("this type not exist"));
       return quizMapper.toDTO(quiz);
    }

    @Override
    public QuizDTO getQuizById(Long id) {
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(()->new RecordNotFoundException("No quiz with id "+id));

        return quizMapper.toResponse(quiz);
    }

    //TODO handle update questions
    @Override
    public String updateQuiz(QuizRequestDto newQuiz, Long quizId) {
        Quiz oldQuiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new RecordNotFoundException("No quiz with id " + quizId));

        oldQuiz.setType(newQuiz.getType());

        List<Question> newQuestions = newQuiz.getQuestions()
                .stream()
                .map(questionMapper::toEntity)
                .peek(question -> question.setQuiz(oldQuiz))  // Associate the new questions with the existing quiz
                .collect(Collectors.toList());

        List<Question> questionsToDelete = oldQuiz.getQuestions().stream()
                .filter(question -> !newQuestions.contains(question))
                .collect(Collectors.toList());

        questionRepo.deleteAll(questionsToDelete);
        oldQuiz.setQuestions(newQuestions);
        quizRepo.save(oldQuiz);

        return "Quiz updated successfully";
    }


}

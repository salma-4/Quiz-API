package org.app.quizapi.controller;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.quiz.QuizDTO;
import org.app.quizapi.dto.quiz.QuizRequestDto;
import org.app.quizapi.dto.quiz.QuizResponseDto;
import org.app.quizapi.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quizApp/v1/quiz")
public class QuizController {
    private final QuizService quizService;

    @GetMapping("/category/{category}")
    public ResponseEntity<QuizRequestDto> getQuizByCategory(@PathVariable String category){
        QuizRequestDto response =quizService.getQuizByType(category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<String> createQuiz(@RequestBody QuizRequestDto quizRequestDto){
        String msg =quizService.createQuiz(quizRequestDto);
        return new ResponseEntity<>(msg,HttpStatus.CREATED);
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long quizId){
        String msg = quizService.deletQuiz(quizId);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }
    @GetMapping("/allCategories")
    public ResponseEntity<List<QuizResponseDto>> getAllQuizzesCategories(){
        List<QuizResponseDto> response = quizService.getAllQuizCategories();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long quizId){
        QuizDTO response = quizService.getQuizById(quizId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

package org.app.quizapi.controller;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.QuizDto;
import org.app.quizapi.dto.QuizResponseDto;
import org.app.quizapi.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quizApp/v1/quiz")
public class QuestionController {
    private final QuizService quizService;

    @GetMapping("/{category}")
    public ResponseEntity<QuizDto> getQuizByCategory(@PathVariable String category){
        QuizDto response =quizService.getQuizByType(category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        String msg =quizService.createQuiz(quizDto);
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
}

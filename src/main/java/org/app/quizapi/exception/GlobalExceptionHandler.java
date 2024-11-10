package org.app.quizapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {RecordNotFoundException.class})
    public ResponseEntity<QuizAppExceptionResponse> handleRecordNotFoundException(RecordNotFoundException exception){
        QuizAppExceptionResponse response= new QuizAppExceptionResponse(
             exception.getMessage(),
             HttpStatus.NOT_FOUND.value(),
             new Date());
     return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}

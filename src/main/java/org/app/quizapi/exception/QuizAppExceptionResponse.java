package org.app.quizapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class QuizAppExceptionResponse {
    private final String message;
    private final int statusCode;
    private final Date timeStamp;
}

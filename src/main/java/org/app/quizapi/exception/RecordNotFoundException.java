package org.app.quizapi.exception;

import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String message) {
        super(message);
    }
}

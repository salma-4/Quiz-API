package org.app.quizapi.exception;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}

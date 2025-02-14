package com.gyma.gyma.exception;

public class StudentLimitExceededException extends RuntimeException {
    public StudentLimitExceededException(String message) {
        super(message);
    }
}

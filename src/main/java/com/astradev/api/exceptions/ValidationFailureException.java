package com.astradev.api.exceptions;

public class ValidationFailureException extends Throwable {
    public ValidationFailureException(String message) {
        super(message);
    }
}
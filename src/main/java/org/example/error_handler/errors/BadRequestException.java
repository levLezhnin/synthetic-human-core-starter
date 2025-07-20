package org.example.error_handler.errors;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Incorrect Request Body");
    }

    public BadRequestException(String message) {
        super(message);
    }
}

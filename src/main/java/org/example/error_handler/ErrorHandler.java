package org.example.error_handler;

import org.example.error_handler.errors.BadRequestException;
import org.example.error_handler.errors.FullTaskQueueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badRequestException(BadRequestException badRequestException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(badRequestException.getMessage());
    }

    @ExceptionHandler(FullTaskQueueException.class)
    public ResponseEntity<String> fullTaskQueueException(FullTaskQueueException fullTaskQueueException) {
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(fullTaskQueueException.getMessage());
    }

}

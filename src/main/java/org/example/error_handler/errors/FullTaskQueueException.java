package org.example.error_handler.errors;

public class FullTaskQueueException extends RuntimeException {
    public FullTaskQueueException() {
        super("Execution queue is full!");
    }
}

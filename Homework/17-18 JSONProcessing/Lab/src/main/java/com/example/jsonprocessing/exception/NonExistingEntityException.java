package com.example.jsonprocessing.exception;

public class NonExistingEntityException extends RuntimeException{
    public NonExistingEntityException() {
        super();
    }

    public NonExistingEntityException(String message) {
        super(message);
    }

    public NonExistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonExistingEntityException(Throwable cause) {
        super(cause);
    }
}

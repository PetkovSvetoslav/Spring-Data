package com.example.jsonprocessing.exception;

public class InvalidEntityDataException extends RuntimeException {
    public InvalidEntityDataException() {
        super();
    }

    public InvalidEntityDataException(String message) {
        super(message);
    }

    public InvalidEntityDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityDataException(Throwable cause) {
        super(cause);
    }
}

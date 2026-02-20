package com.example.sharemate.exceptions;

public class NotFoundedException extends RuntimeException {
    public NotFoundedException(String message) {
        super(message);
    }
}

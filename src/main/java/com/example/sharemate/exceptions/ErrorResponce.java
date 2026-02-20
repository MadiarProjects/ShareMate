package com.example.sharemate.exceptions;

import java.time.Instant;

public class ErrorResponce {
    private String error;

    private final Instant date=Instant.now();

    public ErrorResponce(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public Instant getDate() {
        return date;
    }
}

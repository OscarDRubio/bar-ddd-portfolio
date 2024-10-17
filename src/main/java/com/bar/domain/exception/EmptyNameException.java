package com.bar.domain.exception;

public class EmptyNameException extends RuntimeException {
    public EmptyNameException() {
        super("Name cannot be empty.");
    }
}

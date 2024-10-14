package com.bar.domain.exceptions;

public class EmptyNameException extends RuntimeException {
    public EmptyNameException() {
        super("Name cannot be empty.");
    }
}

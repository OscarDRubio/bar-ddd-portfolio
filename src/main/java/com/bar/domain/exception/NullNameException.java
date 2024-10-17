package com.bar.domain.exception;

public class NullNameException extends RuntimeException {
    public NullNameException() {
        super("Name cannot be null.");
    }
}

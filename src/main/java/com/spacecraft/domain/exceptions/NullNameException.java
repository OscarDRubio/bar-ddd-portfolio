package com.spacecraft.domain.exceptions;

public class NullNameException extends RuntimeException {
    public NullNameException() {
        super("Name cannot be null.");
    }
}

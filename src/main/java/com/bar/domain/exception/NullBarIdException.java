package com.bar.domain.exception;

public class NullBarIdException extends RuntimeException {
    public NullBarIdException() {
        super("BarId cannot be null.");
    }
}

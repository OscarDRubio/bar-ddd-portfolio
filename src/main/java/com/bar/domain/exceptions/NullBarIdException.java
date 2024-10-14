package com.bar.domain.exceptions;

public class NullBarIdException extends RuntimeException {
    public NullBarIdException() {
        super("BarId cannot be null.");
    }
}

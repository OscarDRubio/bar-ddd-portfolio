package com.bar.domain.exception;

public class DuplicateBarTableException extends RuntimeException {
    public DuplicateBarTableException(String message) {
        super(message);
    }
}

package com.bar.domain.exception;

public class DuplicateBarException extends Exception {
    public DuplicateBarException(String message) {
        super(message);
    }
}
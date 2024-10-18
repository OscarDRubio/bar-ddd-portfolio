package com.bar.domain.exception;

public class DuplicateBarException extends RuntimeException {
    public DuplicateBarException() {
        super("The bar already exists");
    }
}

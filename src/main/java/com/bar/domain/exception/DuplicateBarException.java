package com.bar.domain.exception;

public class DuplicateBarException extends Exception {
    public DuplicateBarException() {
        super("The bar already exists");
    }
}

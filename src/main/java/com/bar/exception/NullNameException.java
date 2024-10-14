package com.bar.exception;

public class NullNameException extends RuntimeException {
    public NullNameException() {
        super("Name can not be null");
    }
}

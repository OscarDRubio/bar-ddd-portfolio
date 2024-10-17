package com.bar.domain.exception;

public class NullPriceException extends RuntimeException {
    public NullPriceException() {
        super("Price can not be null");
    }
}

package com.bar.exception;

import java.sql.SQLException;

public class DuplicateBarException extends SQLException {
    public DuplicateBarException(String message) {
        super(message);
    }
}

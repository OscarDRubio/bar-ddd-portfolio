package com.spacecraft.exception;

import java.sql.SQLException;

public class NullNameException extends SQLException {
    public NullNameException(String message) {
        super(message);
    }
}

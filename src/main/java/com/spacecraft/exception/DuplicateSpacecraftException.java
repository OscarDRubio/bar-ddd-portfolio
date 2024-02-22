package com.spacecraft.exception;

import java.sql.SQLException;

public class DuplicateSpacecraftException extends SQLException {
    public DuplicateSpacecraftException(String message) {
        super(message);
    }
}

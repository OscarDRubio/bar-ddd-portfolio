package com.spacecraft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateSpacecraftException.class)
    public ResponseEntity<Object> handleDuplicateSpacecraftException(DuplicateSpacecraftException exception) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Error loading the spacecraft");
        body.put("error", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(NullNameException.class)
    public ResponseEntity<Object> handleNullNameException(NullNameException exception) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Name cannot be null");
        body.put("error", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

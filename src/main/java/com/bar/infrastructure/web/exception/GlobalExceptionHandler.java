package com.bar.infrastructure.web.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.exception.DuplicateBarTableException;
import com.bar.domain.exception.EmptyNameException;
import com.bar.domain.exception.IllegalDecimalException;
import com.bar.domain.exception.NegativePriceException;
import com.bar.domain.exception.NullNameException;

import jakarta.persistence.EntityNotFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateBarException.class)
    public ResponseEntity<Object> handleDuplicateBarException(DuplicateBarException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
    @ExceptionHandler(DuplicateBarTableException.class)
    public ResponseEntity<Object> handleDuplicateBarTableException(DuplicateBarTableException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
    @ExceptionHandler(EmptyNameException.class)
    public ResponseEntity<Object> handleEmptyNameException(EmptyNameException exception) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Name cannot be empty");
        body.put("error", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullNameException.class)
    public ResponseEntity<Object> handleNullNameException(NullNameException exception) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Name cannot be null");
        body.put("error", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Entity not found");
        body.put("error", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Data integrity violation");
        body.put("error", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(NegativePriceException.class)
    public ResponseEntity<Object> handleNegativePriceException(NegativePriceException exception) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Price can not be negative");
        body.put("error", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalDecimalException.class)
    public ResponseEntity<Object> handleIllegalDecimalException(IllegalDecimalException exception) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Price cannot have more than two decimal places");
        body.put("error", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

package com.spacecraft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spacecraft.domain.spacecraft.Name;

public class NameTests  {

    @Test()
    @DisplayName("""
        When I try to make a NULL name
        Then it throws an error with the correct message
    """)
    void name_null_exception() throws Exception {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new Name(null);
        });

        assertEquals(exception.getMessage(), "Name cannot be null.");
    }

    @Test()
    @DisplayName("""
        When I try to make an empty name
        Then it throws an error with the correct message
    """)
    void name_empty_exception() throws Exception {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new Name("");
        });

        assertEquals(exception.getMessage(), "Name cannot be empty.");
    } 
}

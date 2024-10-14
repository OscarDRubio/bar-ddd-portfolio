package com.bar;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bar.domain.exceptions.EmptyNameException;
import com.bar.domain.exceptions.NullNameException;
import com.bar.domain.shared.Name;

public class NameTests  {

    @Test()
    @DisplayName("""
        When I try to make a NULL name
        Then it throws a NullNameException
    """)
    void name_null_exception() throws Exception {
        assertThrows(NullNameException.class, () -> {
            new Name(null);
        });
    }

    @Test()
    @DisplayName("""
        When I try to make an empty name
        Then it throws an error with the correct message
    """)
    void name_empty_exception() throws Exception {
        assertThrows(EmptyNameException.class, () -> {
            new Name("");
        });
    } 
}

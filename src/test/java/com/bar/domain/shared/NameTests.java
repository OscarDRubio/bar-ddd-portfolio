package com.bar.domain.shared;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.bar.domain.exception.EmptyNameException;
import com.bar.domain.exception.NullNameException;

@ActiveProfiles("test")
public class NameTests  {

    @Test()
    @DisplayName("""
        When I try to make a NULL name
        Then it throws a NullNameException
    """)
    void createWithNullName() throws Exception {
        assertThrows(NullNameException.class, () -> {
            new Name(null);
        });
    }

    @Test()
    @DisplayName("""
        When I try to make an empty name
        Then it throws an EmptyNameException
    """)
    void createWithEmptyName() throws Exception {
        assertThrows(EmptyNameException.class, () -> {
            new Name("");
        });
    } 
}

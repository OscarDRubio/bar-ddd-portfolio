package com.bar.domain.bar;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.bar.domain.exception.NullNameException;

public class BarTests {

    @Test()
    @DisplayName("""
        When I try to create a new Bar with null name
        Then it throws a NullNameException
    """)
    void nullName() throws Exception {

        assertThrows(NullNameException.class, () -> 
            new Bar(null)
        );
    }
}

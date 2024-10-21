package com.bar.domain.bar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bar.domain.exception.NullNameException;
import com.bar.domain.shared.Name;

public class BarTests {

    @Test()
    @DisplayName("""
        When I try to create a valid new Bar with null id
        Then its state is correct
    """)
    void createWithNullId() throws Exception {

        Bar bar = new Bar(new Name("Bar Test"));
        assertNotNull(bar.toDto().id());
        assertEquals(bar.toDto().name(), "Bar Test");
    }

    @Test()
    @DisplayName("""
        When I try to create a valid new Bar
        Then its state is correct
    """)
    void createWithId() throws Exception {

        Bar bar = new Bar(new BarId("BarId"), new Name("Bar Test"));
        assertEquals(bar.toDto(), new BarDto("BarId", "Bar Test"));
    }

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

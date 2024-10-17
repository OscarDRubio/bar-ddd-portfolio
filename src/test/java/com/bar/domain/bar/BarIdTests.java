package com.bar.domain.bar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class BarIdTests  {

    @Test()
    @DisplayName("""
        When I try to create a new BarId without parameter
        Then it returns an UIID
    """)
    void createUuidId() throws Exception {
        BarId id = new BarId();
        String uuidString = id.toString();
        validateUUID(uuidString);
    }

    @Test()
    @DisplayName("""
        When I try to create a new BarId with parameter
        Then it creates an Id with the parameter
    """)
    void createGivenId() throws Exception {
        BarId id = new BarId("givenId");
        assertEquals("givenId", id.toString());
    } 

    private void validateUUID(String uuidString) {
    
        assertEquals(36, uuidString.length(), "The UUID should have 36 characters");
    
        assertEquals('-', uuidString.charAt(8), "The character at position 8 should be a dash");
        assertEquals('-', uuidString.charAt(13), "The character at position 13 should be a dash");
        assertEquals('-', uuidString.charAt(18), "The character at position 18 should be a dash");
        assertEquals('-', uuidString.charAt(23), "The character at position 23 should be a dash");
    
        assertTrue(uuidString.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"),
                "The UUID should follow the standard format");
    }
}

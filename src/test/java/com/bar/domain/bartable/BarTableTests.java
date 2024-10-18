package com.bar.domain.bartable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.bar.application.BarService;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.exception.DuplicateBarTableException;
import com.bar.domain.exception.EmptyNameException;
import com.bar.domain.exception.NullBarIdException;
import com.bar.domain.exception.NullNameException;
import com.bar.domain.shared.Name;
import com.bar.domain.table.BarTable;

@SpringBootTest
@ActiveProfiles("test")
public class BarTableTests {

    @Autowired
    private BarService barService;

    @Test()
    @DisplayName("""
        When I try to create a new BarTable
        Then it is not null and have the correct name and barId
    """)
    void create() throws Exception {

        Bar bar = createBar("Llúpol");
        BarTable barTable = new BarTable(
            new Name("Mesa 1"), 
            new BarId(bar.getId().toString()));

        assertNotNull(barTable);
        assertNotNull(barTable.getId());
        assertEquals("Mesa 1", barTable.getName().toString());
        assertEquals(bar.getId().toString(), barTable.getBarId().toString());
    }

    @Test()
    @DisplayName("""
        When I try to create and save a duplicated BarTable
        Then it returns a DataIntegrityViolationException 
    """)
    void createDuplicate() throws Exception {

        Bar bar = createBar("Pizzeria Rosi");

        BarTable barTable = new BarTable(
            new Name("Mesa 1"), 
            new BarId(bar.getId().toString()));

        barService.createBarTable(barTable);

        BarTable barTable2 = new BarTable(
            new Name("Mesa 1"), 
            new BarId(bar.getId().toString()));

        assertThrows(DuplicateBarTableException.class, () -> {
            barService.createBarTable(barTable2);
        });
    }

    @Test()
    @DisplayName("""
        When I try to create a BarTable without BarId
        Then it returns a NullBarIdException 
    """)
    void createWithNullBarId() throws Exception {

        assertThrows(NullBarIdException.class, () -> {
            new BarTable(
                new Name("Mesa 1"), 
                null);
        });
     }

    @Test()
    @DisplayName("""
        When I try to create a BarTable with empty name
        Then it returns a EmptyNameException
    """)
    void createWithEmptyName() throws Exception {
 
        Bar bar = createBar("Nou Granada");
        assertThrows(EmptyNameException.class, () -> {
            new BarTable(
                new Name(""), 
                new BarId(bar.getId().toString()));
        });
    }

    @Test()
    @DisplayName("""
        When I try to create a BarTable with null name
        Then it returns a NullNameException
    """)
    void createWithNullName() throws Exception {
 
        Bar bar = createBar("Standby");
        assertThrows(NullNameException.class, () -> {
            new BarTable(null, bar.getId());
        });
    }

    private Bar createBar(String barName) throws DuplicateBarException {
        return barService.create(new Bar(new Name(barName)));
    }

}

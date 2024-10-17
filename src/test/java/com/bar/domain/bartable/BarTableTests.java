package com.bar.domain.bartable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.exception.EmptyNameException;
import com.bar.domain.exception.NullBarIdException;
import com.bar.domain.exception.NullNameException;
import com.bar.domain.shared.Name;
import com.bar.domain.table.BarTable;
import com.bar.infrastructure.repository.BarRepository;
import com.bar.infrastructure.repository.BarTableRepository;

@DataJpaTest
@ActiveProfiles("test")
public class BarTableTests {

    @Autowired
    private BarTableRepository barTableRepository;

    @Autowired
    private BarRepository barRepository;

    @Test()
    @DisplayName("""
        When I try to create a new BarTable
        Then it is not null and have the correct name and barId
    """)
    void create() throws Exception {

        Bar bar = createBar();
        BarTable barTable = new BarTable(
            new Name("Mesa 1"), 
            new BarId(bar.getId().toString()));

        assertNotNull(barTable);
        assertNotNull(barTable.getId());
        assertEquals("Mesa 1", barTable.getName());
        assertEquals(bar.getId().toString(), barTable.getBarId().toString());
    }

    @Test()
    @DisplayName("""
        When I try to create and save a duplicated BarTable
        Then it returns a DataIntegrityViolationException 
    """)
    void createDuplicate() throws Exception {

        Bar bar = createBar();

        BarTable barTable = new BarTable(
            new Name("Mesa 1"), 
            new BarId(bar.getId().toString()));

        barTableRepository.create(barTable);

        BarTable barTable2 = new BarTable(
            new Name("Mesa 1"), 
            new BarId(bar.getId().toString()));

        assertThrows(DataIntegrityViolationException.class, () -> {
            barTableRepository.create(barTable2);
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
 
        Bar bar = createBar();
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
 
        Bar bar = createBar();
        assertThrows(NullNameException.class, () -> {
            new BarTable(null, bar.getId());
        });
    }

    private Bar createBar() throws DuplicateBarException {
        return barRepository.create(new Bar(new Name("Llúpol")));
    }

}

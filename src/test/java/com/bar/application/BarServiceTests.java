package com.bar.application;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.bar.domain.exception.DuplicateBarException;
import com.bar.infrastructure.repository.bar.BarRepository;

import jakarta.transaction.Transactional;
import junit.framework.TestCase;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BarServiceTests extends TestCase {

    private final BarRepository barRepository;

    public BarServiceTests(@Autowired BarRepository barRepository) {
        this.barRepository = barRepository;
    }

    @Test
    @DisplayName("""
        When I try to create two Bar
        With the same idBar
        Then it returns a DuplicatedBarException
    """)
    void createDuplicatedIdBar() throws Exception {

        String barId = "Mulligan Id";
        String barName = "Mulligan's";

        barRepository.create(barId, barName);

        assertThrows(DuplicateBarException.class, () -> 
            barRepository.create(barId, barName)
        );
    }

    @Test
    @DisplayName("""
        When I try to create two Bar
        With the same Name
        Then it returns a DuplicatedBarException
    """)
    void createDuplicatedName() throws Exception {

        String barName = "Mulligan's";

        barRepository.create(barName);

        assertThrows(DuplicateBarException.class, () -> 
            barRepository.create(barName)
        );
    }

    //TODO: Complete the test
    /**
    @Test
    @DisplayName("""
        When I try to create two BarTables
        With the same name in the same Bar
        Then it returns a 409 Conflict and a DataIntegrityViolationException
    """)
    void createDuplicateBarTableNameInSameBar() throws Exception {

        Bar bar = new Bar(new Name("Lo de Ponxe en el Kinto Pino"));

        bar = barRepository.create(bar);


    }
    **/
}

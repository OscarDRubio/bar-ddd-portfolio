package com.bar.infrastructure;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bar.domain.exception.DuplicateBarException;
import com.bar.infrastructure.repository.bar.BarRepository;

//TODO: Remove SpringBootTest full load
@SpringBootTest
public class BarRepositoryTests {

    private BarRepository barRepository;

    @Autowired
    public BarRepositoryTests(BarRepository barRepository) {
        this.barRepository = barRepository;
    }

    @Test
    @DisplayName("""
        When I try to create a duplicated id bar
        Then it throws a DuplicatedBarException
    """)
    void duplicateId() {

        String barId = "Duplicated Bar Id";
        String name1 = "Bar One";

        barRepository.create(barId, name1);

        String name2 = "Bar Two";

        assertThrows(DuplicateBarException.class, () -> 
            barRepository.create(barId, name2)
        );
    }

    @Test
    @DisplayName("""
        When I try to create a duplicated name bar
        Then it throws a DuplicatedBarException
    """)
    void duplicateName() {

        String name = "Mariano's";

        barRepository.create(name);

        assertThrows(DuplicateBarException.class, () -> 
            barRepository.create(name)
        );
    }
}

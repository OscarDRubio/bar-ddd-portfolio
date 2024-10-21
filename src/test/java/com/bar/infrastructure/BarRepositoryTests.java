package com.bar.infrastructure;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.shared.Name;
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

        BarId barId = new BarId("BarId");

        Bar bar = new Bar(barId, new Name("BarTest1"));

        barRepository.save(bar);
        
        Bar duplicatedBar = new Bar(barId, new Name("BarTest2"));

        assertThrows(DuplicateBarException.class, () -> 
            barRepository.save(duplicatedBar)
        );
    }

    @Test
    @DisplayName("""
        When I try to create a duplicated name bar
        Then it throws a DuplicatedBarException
    """)
    void duplicateName() {

        Bar bar = new Bar(new Name("BarTest"));

        barRepository.save(bar);
        
        Bar duplicatedBar = new Bar(new Name("BarTest"));

        assertThrows(DuplicateBarException.class, () -> 
            barRepository.save(duplicatedBar)
        );
    }
}

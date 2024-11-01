package com.bar.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.shared.Name;
import com.bar.infrastructure.repository.bar.BarRepositoryImpl;
import jakarta.transaction.Transactional;

@ActiveProfiles("test")
// @Transactional
@SpringBootTest()   
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DataJpaTest

public class BarIntegrationTests {

    @Autowired
    BarRepositoryImpl barRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("""
        When I try to create a valid Bar
        Then it returns a DuplicatedBarException
    """)
    void createBar() {

        // String barIdString = "unique-id";
        // String barNameString = "Unique Bar";
        
        // Bar bar = new Bar(
        //     new BarId(barIdString), 
        //     new Name(barNameString));

        // barRepository.save(bar);

        // verify(barRepository).save(any(Bar.class)); 
    }

    /**
    @Test
    @DisplayName("""
        When I try to create two Bar
        With the same idBar
        Then it returns a DuplicatedBarException
    """)
    void createDuplicatedIdBar() {

        String barIdString = "existing-id";
        String barName = "Another Bar";
        CreateBarCommand command = new CreateBarCommand(barIdString, barName);

        Bar existingBar = new Bar(new BarId(barIdString), new Name("Existing Bar"));
        when(barRepository.findById(new BarId(barIdString))).thenReturn(Optional.of(existingBar));

        assertThrows(DuplicateBarException.class, () -> barService.createBar(command));

        verify(barRepository, never()).save(any(Bar.class)); 
    }

    @Test
    @DisplayName("""
        When I try to create two Bar
        With the same Name
        Then it returns a DuplicatedBarException
    """)
    void createDuplicatedName() {

        String barName = "Mulligan's";
        CreateBarCommand command = new CreateBarCommand(barName);

        Bar existingBar = new Bar(new BarId("123"), new Name(barName));
        when(barRepository.findByName(new Name(barName))).thenReturn(Optional.of(existingBar));

        assertThrows(DuplicateBarException.class, () -> barService.createBar(command));

        verify(barRepository, never()).save(any(Bar.class)); 
    }
         */

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

        bar = barService.create(bar);


    }
    **/
}

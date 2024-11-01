package com.bar.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.bar.application.bar.BarService;
import com.bar.application.bar.command.CreateBarCommand;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarDto;
import com.bar.domain.bar.BarId;
import com.bar.domain.bar.IBarRepository;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.shared.Name;
import junit.framework.TestCase;

@ExtendWith(MockitoExtension.class)
public class BarServiceTests extends TestCase {

    @Mock
    private IBarRepository barRepository;

    @InjectMocks
    private BarService barService;

    void createBar() {

        String barIdString = "unique-id";
        String barName = "Unique Bar";
        CreateBarCommand command = new CreateBarCommand(barIdString, barName);

        when(barRepository.findById(new BarId(barIdString))).thenReturn(Optional.empty());
        when(barRepository.findByName(new Name(barName))).thenReturn(Optional.empty());

        BarDto createdBar = barService.createBar(command);

        assertNotNull(createdBar);
        assertEquals(barIdString, createdBar.getId());
        assertEquals(barName, createdBar.getName());
        verify(barRepository).save(any(Bar.class)); 
    }

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

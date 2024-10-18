package com.bar.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.bar.domain.bar.Bar;
import com.bar.domain.shared.Name;
import jakarta.transaction.Transactional;
import junit.framework.TestCase;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class BarServiceTests extends TestCase {

    private final BarService barService;

    public BarServiceTests(@Autowired BarService barService) {
        this.barService = barService;
    }

    //TODO: Complete the test
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
}

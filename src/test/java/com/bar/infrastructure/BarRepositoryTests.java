package com.bar.infrastructure;

import org.springframework.boot.test.context.SpringBootTest;

//TODO: Remove SpringBootTest full load
@SpringBootTest
public class BarRepositoryTests {

    /**
    private BarRepositoryImpl barRepository;

    @Autowired
    public BarRepositoryTests(BarRepositoryImpl barRepository) {
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

    **/
}

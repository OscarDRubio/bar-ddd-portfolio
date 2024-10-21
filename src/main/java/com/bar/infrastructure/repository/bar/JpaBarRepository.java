package com.bar.infrastructure.repository.bar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import java.util.Optional;

public interface JpaBarRepository extends JpaRepository<Bar, BarId> {

    Page<Bar> findByName_NameContaining(String name, Pageable pageable);
    Optional<Bar> findByName_Name(String name);

    @Override
    @NonNull
    Page<Bar> findAll(@NonNull Pageable pageable); 

    default Optional<Bar> findById(String barIdString) {
        BarId barId = new BarId(barIdString);
        return findById(barId);
    }

    default void deleteById(String barIdString) {
        BarId barId = new BarId(barIdString);
        deleteById(barId);
    }
}

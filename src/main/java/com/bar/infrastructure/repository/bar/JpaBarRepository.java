package com.bar.infrastructure.repository.bar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;

import java.util.Optional;

@Repository
public interface JpaBarRepository extends JpaRepository<Bar, BarId> {
    Optional<Bar> findByName_Name(String name);
    Page<Bar> findByName_NameContaining(String name, Pageable pageable);
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

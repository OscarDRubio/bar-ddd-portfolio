package com.bar.infrastructure.repository.bar;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;

import java.util.List;
import java.util.Optional;

public interface JpaBarRepository extends JpaRepository<Bar, BarId> {

    Optional<Bar> findByName_Name(String name);
    List<Bar> findByName_NameContaining(String name);
    boolean existsByNameAndIdNot(String name, String id);
}

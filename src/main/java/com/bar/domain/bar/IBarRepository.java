package com.bar.domain.bar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBarRepository extends JpaRepository<Bar, BarId> {
    Page<Bar> findByName_NameContaining(String name, Pageable pageable);
    Optional<Bar> findById_Id(String id);
    Optional<Bar> findByName_Name(String name);
    boolean existsByName_NameAndIdIsNot(String name, BarId id);
}

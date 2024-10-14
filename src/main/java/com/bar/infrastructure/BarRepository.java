package com.bar.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BarRepository extends JpaRepository<Bar, BarId> {

    Page<Bar> findByNameContaining(String keyword, Pageable pageable);
    boolean existsByName(String name);
}

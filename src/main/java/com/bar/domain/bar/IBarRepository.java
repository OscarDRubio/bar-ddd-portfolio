package com.bar.domain.bar;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bar.domain.shared.Name;

public interface IBarRepository {
    Optional<Bar> findById(BarId id);
    Optional<Bar> findByName(Name name);
    Page<Bar> findByNameContaining(String keyword, Pageable pageable);
    void save(Bar bar);
    void deleteById(BarId id);
    Page<Bar> findAll(Pageable pageable);
}

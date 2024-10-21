package com.bar.domain.bar;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBarRepository {
    Optional<Bar> findById(String id);
    Optional<Bar> findByName(String name);
    void deleteById(String id);
    Bar create(String name);
    Bar update(String id, String name);
    Page<Bar> findByNameContaining(String keyword, Pageable pageable);
    Page<Bar> findAll(Pageable pageable);
}

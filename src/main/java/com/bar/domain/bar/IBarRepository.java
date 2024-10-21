package com.bar.domain.bar;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBarRepository {
    Optional<Bar> findById(String id);
    Page<Bar> findByNameContaining(String keyword, Pageable pageable);
    void deleteById(String id);
    Page<Bar> findAll(Pageable pageable);
    Bar create(String name);
    Bar update(String id, String name);
}

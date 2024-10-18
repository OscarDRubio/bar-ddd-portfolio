package com.bar.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.shared.Name;
import com.bar.domain.bar.IBarRepository;
import com.bar.domain.exception.DuplicateBarException;

import java.util.Optional;

@Repository
public class BarRepository implements IBarRepository {

    private final JpaBarRepository jpaBarRepository;

    @Autowired
    public BarRepository(JpaBarRepository jpaBarRepository) {
        this.jpaBarRepository = jpaBarRepository;
    }

    @Override
    public Optional<Bar> findById(BarId id) {
        return jpaBarRepository.findById(id);
    }

    @Override
    public Optional<Bar> findByName(Name name) {
        return jpaBarRepository.findByName_Name(name.getName());
    }

    @Override
    public Page<Bar> findByNameContaining(String keyword, Pageable pageable) {
        return jpaBarRepository.findByName_NameContaining(keyword, pageable);
    }

    @Override
    public void save(Bar bar) {

        try {
            jpaBarRepository.save(bar);
        }
        catch (DataIntegrityViolationException ex) {
            throw new DuplicateBarException();
        }
    }

    @Override
    public void deleteById(BarId id) {
        jpaBarRepository.deleteById(id);
    }

    @Override
    public Page<Bar> findAll(Pageable pageable) {
        return jpaBarRepository.findAll(pageable);
    }
}

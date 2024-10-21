package com.bar.infrastructure.repository.bar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.shared.Name;

import jakarta.persistence.EntityNotFoundException;

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
    public Bar create(String name) {

        return create(null, name);
    }

    public Bar create(String barId, String name) {

        if(barId != null) {
            findById(barId)
                .ifPresent(bar -> {
                    throw new DuplicateBarException();
                });
        }

        Bar bar = new Bar(
            new BarId(barId), 
            new Name(name));

        jpaBarRepository.save(bar);
        return bar;
    }

    @Override
    public Bar update(String barId, String name) {

        findById(barId)
             .orElseThrow(() -> new EntityNotFoundException("Bar with id " + barId + " not found"));

        Bar bar = new Bar(new BarId(barId), new Name(name));
        return jpaBarRepository.save(bar);
    }

    @Override
    public Page<Bar> findByNameContaining(String keyword, Pageable pageable) {
        return jpaBarRepository.findByName_NameContaining(keyword, pageable);
    }

    @Override
    public void deleteById(String id) {
        jpaBarRepository.deleteById(id);
    }

    @Override
    public Page<Bar> findAll(Pageable pageable) {
        return jpaBarRepository.findAll(pageable);
    }

    @Override
    public Optional<Bar> findById(String id) {
        return jpaBarRepository.findById(id);
    }
}

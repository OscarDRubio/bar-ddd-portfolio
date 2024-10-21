package com.bar.infrastructure.repository.bar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.shared.Name;
import com.bar.infrastructure.web.controller.dto.BarRequest;

import jakarta.persistence.EntityNotFoundException;

import com.bar.domain.bar.IBarRepository;
import com.bar.domain.exception.DuplicateBarException;

import java.util.Optional;

@Repository
public class BarRepository implements IBarRepository {

    private final JpaBarRepository jpaBarRepository;

    public Bar create(BarRequest createBarRequest) {

        return create(null, createBarRequest);
    }

    public Bar create(String barId, BarRequest createBarRequest) {

        if(barId != null) {
            findById(barId)
                .ifPresent(bar -> {
                    throw new DuplicateBarException();});
        }

        Bar bar = new Bar(
            new BarId(barId), 
            new Name(createBarRequest.getName()));

        save(bar);
        return bar;
    }

    public void update(String barId, BarRequest updateBarRequest) {

        findById(barId)
             .orElseThrow(() -> new EntityNotFoundException("Bar with id " + barId + " not found"));

        Bar bar = new Bar(new BarId(barId), new Name(updateBarRequest.getName()));
        save(bar);
    }

    @Autowired
    public BarRepository(JpaBarRepository jpaBarRepository) {
        this.jpaBarRepository = jpaBarRepository;
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

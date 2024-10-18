package com.bar.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.exception.DuplicateBarTableException;
import com.bar.domain.shared.Name;
import com.bar.domain.table.BarTable;
import com.bar.infrastructure.repository.BarRepository;
import com.bar.infrastructure.web.controller.dto.CreateBarRequest;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BarService {

    private final BarRepository barRepository;
    private final BarTableService barTableService;

    @Autowired
    public BarService(BarRepository barRepository, BarTableService barTableService) {
        this.barRepository = barRepository;
        this.barTableService = barTableService;
    }

    public Bar create(Bar bar) throws DuplicateBarException {

        if (barRepository.existsByNameAndAnotherId(bar)) {
            throw new DuplicateBarException("The bar name '" + bar.getName().getName() + "' already exists.");
        }

        return barRepository.save(bar);
    }

    public Bar update(BarId barId, CreateBarRequest updateBarRequest) throws DuplicateBarException {

        Bar bar = barRepository.findById(barId)
             .orElseThrow(() -> new EntityNotFoundException("Bar with id " + barId.getId().toString() + " not found"));

        bar.setName(new Name(updateBarRequest.getName()));
        if (barRepository.existsByNameAndAnotherId(bar)) {
            throw new DuplicateBarException("A bar with the name '" + bar.getName().getName() + "' already exists.");
        }
        return barRepository.save(bar);
    }
    
    public Page<Bar> findAll(Pageable pageable) {
        return barRepository.findAll(pageable);
    }

    public Page<Bar> findByNameContaining(String keyword, Pageable pageable) {
        return barRepository.findByNameContaining(keyword, pageable);
    }

    public Optional<Bar> findById(String id) {
        return barRepository.findById(new BarId(id));
    }

    public void deleteById(String id) {
        barRepository.deleteById(new BarId(id));
    }

    public BarTable createBarTable(BarTable barTable) throws DuplicateBarTableException {

        barRepository.findById(barTable.getBarId())
             .orElseThrow(() -> new EntityNotFoundException("Bar with id " + barTable.getBarId().toString() + " not found"));

        return barTableService.create(barTable);
    }
}

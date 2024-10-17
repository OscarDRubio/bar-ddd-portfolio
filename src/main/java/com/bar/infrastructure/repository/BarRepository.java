package com.bar.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.shared.Name;
import com.bar.domain.bar.IBarRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class BarRepository {

    @Autowired
    private IBarRepository barRepository;

    public <S extends Bar> S create(S entity) throws DuplicateBarException {

        if (existsByName(entity.getName())) {
            throw new DuplicateBarException("The bar name '" + entity.getName().getName() + "' already exists.");
        }
        return barRepository.save(entity); 
    }

    public Bar update(Bar entity) throws DuplicateBarException {

        if (barRepository.existsByName_NameAndIdIsNot(entity.getName().getName(), entity.getId())) {
            throw new DuplicateBarException("A bar with the name '" + entity.getName().getName() + "' already exists.");
        }

        return barRepository.save(entity);
    }

    private boolean existsByName(Name name) {
        return barRepository.findByName_Name(name.getName()).isPresent();
    }

    public Optional<Bar> findByName(Name name) {
        return barRepository.findByName_Name(name.toString());
    }

    public List<Bar> findAll() {
        return barRepository.findAll();
    }

    public Optional<Bar> findById(BarId id) {
        return barRepository.findById_Id(id.toString());
    }

    public void deleteById(BarId id) {
        barRepository.deleteById(id);
    }

    public void delete(Bar bar) {
        barRepository.delete(bar);
    }
    
    public Page<Bar> findAll(Pageable pageable) {
        return barRepository.findAll(pageable);
    }

    public Page<Bar> findByNameContaining(String name, Pageable pageable) {
        return barRepository.findByName_NameContaining(name, pageable);
    }
}

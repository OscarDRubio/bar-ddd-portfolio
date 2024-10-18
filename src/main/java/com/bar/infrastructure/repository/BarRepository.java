package com.bar.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.shared.Name;
import com.bar.domain.bar.IBarRepository;

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
    public boolean existsByNameAndAnotherId(Bar bar) {
        return jpaBarRepository.existsByName_NameAndIdIsNot(bar.getName().toString(), bar.getId());
    }

    @Override
    public Bar save(Bar bar) {
        return jpaBarRepository.save(bar);
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

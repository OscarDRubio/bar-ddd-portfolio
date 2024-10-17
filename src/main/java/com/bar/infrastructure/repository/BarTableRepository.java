package com.bar.infrastructure.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bar.domain.bar.BarId;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.shared.Name;
import com.bar.domain.table.BarTable;
import com.bar.domain.table.IBarTableRepository;

@Repository
public class BarTableRepository {

    @Autowired
    private IBarTableRepository barTableRepository;

    public <S extends BarTable> S create(S entity) throws DuplicateBarException {

        if (existsByNameAndBarId(entity.getName(), entity.getBarId())) {
            throw new DuplicateBarException("The table name '" + entity.getName().toString() + "' already exists.");
        }
        return barTableRepository.save(entity); 
    }

    public BarTable update(BarTable entity) throws DuplicateBarException {

        return barTableRepository.save(entity);
    }

    private boolean existsByNameAndBarId(Name name, BarId barId) {
        return barTableRepository.findByNameAndBarId(name, barId).isPresent();
    }
    
    public List<BarTable> findByBarId(BarId barId) {
        return barTableRepository.findByBarId(barId);
    }
}

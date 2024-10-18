package com.bar.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bar.domain.exception.DuplicateBarTableException;
import com.bar.domain.table.BarTable;
import com.bar.infrastructure.repository.BarTableRepository;

@Service
public class BarTableService {

    private final BarTableRepository barTableRepository;

    @Autowired
    public BarTableService(BarTableRepository barTableRepository) {
        this.barTableRepository = barTableRepository;
    }

    public BarTable create(BarTable barTable) throws DuplicateBarTableException {

        if (barTableRepository.existsByNameAndBarId(barTable)) {
            throw new DuplicateBarTableException("The table name '" + barTable.getName().getName() + "' already exists.");
        }

        return barTableRepository.save(barTable);
    }
}
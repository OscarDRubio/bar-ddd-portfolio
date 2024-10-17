package com.bar.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bar.domain.bar.Bar;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.shared.Name;
import com.bar.infrastructure.repository.BarRepository;

@Service
public class BarService {

    @Autowired
    private BarRepository barRepository;

    public Bar createBar(Name name) throws DuplicateBarException {

        try {

            Bar bar = new Bar(name);
            return barRepository.create(bar);
        } 
        catch (DataIntegrityViolationException e) {

            throw new DuplicateBarException("The bar name '" + name + "' already exists.");
        }
    }
}
package com.bar.application.bar;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bar.application.bar.dto.BarRequest;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarDto;
import com.bar.domain.bar.BarId;
import com.bar.domain.bar.IBarRepository;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.shared.Name;
import com.bar.domain.shared.Pagination;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BarService {

    private final IBarRepository barRepository;

    public BarService(IBarRepository barRepository) {
        this.barRepository = barRepository;
    }

    public List<Bar> findAllPaginated(int page, int size) {
        Pagination pagination = new Pagination(page, size);
        return barRepository.findAllPaginated(pagination);
    }


    public Optional<Bar> findById(String barIdString) {
        return barRepository.findById(new BarId(barIdString));
    }

    public BarDto createBar(BarRequest request) {

        if (barRepository.findByName(new Name(request.name)).isPresent()) {
            throw new DuplicateBarException();
        }

        Bar bar = new Bar(new Name(request.name));
        Bar savedBar = barRepository.save(bar);
        return savedBar.toDto();
    }

    public BarDto updateBar(String id, BarRequest request) {

        Optional<Bar> barOptional = findById(id);

        if(!barOptional.isPresent()) 
            throw new EntityNotFoundException("The BarId does not exist.");

        if(barRepository.existsByNameAndDifferentId(
            new Name(request.name), new BarId(id))) {

            throw new DuplicateBarException();
        }

        Bar bar = new Bar(new BarId(id), new Name(request.name));
        Bar updatedBar = barRepository.save(bar);
        return updatedBar.toDto();
    }

    public List<Bar> findByNameContaining(String name) {
        return barRepository.findByNameContaining(new Name(name));
    }

    public void deleteById(String barIdString) {
        
        BarId barId = new BarId(barIdString);
        barRepository.deleteById(barId);
    }
}

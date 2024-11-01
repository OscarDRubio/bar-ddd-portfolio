package com.bar.application.bar;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bar.application.bar.command.CreateBarCommand;
import com.bar.application.bar.command.UpdateBarCommand;
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

        BarId barId = new BarId(barIdString);
        return barRepository.findById(barId);
    }

    public BarDto createBar(CreateBarCommand command) {

        if (barRepository.findByName(new Name(command.getName())).isPresent()) {
            throw new DuplicateBarException();
        }

        Bar bar = new Bar(new Name(command.getName()));
        Bar savedBar = barRepository.save(bar);
        return savedBar.toDto();
    }

    public BarDto updateBar(UpdateBarCommand command) {

        Optional<Bar> barOptional = findById(command.getId());

        if(!barOptional.isPresent()) 
            throw new EntityNotFoundException("The BarId does not exist.");

        if(barRepository.existsByNameAndDifferentId(
            new Name(command.getName()), new BarId(command.getId()))) {

            throw new DuplicateBarException();
        }

        Bar bar = new Bar(new BarId(command.getId()), new Name(command.getName()));
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

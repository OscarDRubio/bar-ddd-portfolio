package com.bar.infrastructure.repository.bar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.shared.Name;
import com.bar.domain.shared.Pagination;

import jakarta.persistence.EntityNotFoundException;

import com.bar.domain.bar.IBarRepository;

import java.util.List;
import java.util.Optional;

@Component
public class BarRepositoryImpl implements IBarRepository {

    private final JpaBarRepository jpaBarRepository;

    @Autowired
    public BarRepositoryImpl(JpaBarRepository jpaBarRepository) {
        this.jpaBarRepository = jpaBarRepository;
    }

    @Override
    public Bar findById(BarId id) {
        Optional<Bar> barOptional = jpaBarRepository.findById(id);
        if(!barOptional.isPresent()) 
            throw new EntityNotFoundException("The barId does not exist.");
        return barOptional.get();
    }

    @Override
    public List<Bar> findAllPaginated(Pagination pagination) {
        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getSize());
        return jpaBarRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<Bar> findByNameContaining(Name name) {
        return jpaBarRepository.findByName_NameContaining(name.getName());
    }

    @Override
    public Optional<Bar> findByName(Name name) {
        return jpaBarRepository.findByName_Name(name.toString());
    }

    @Override
    public void save(Bar bar) {
        jpaBarRepository.save(bar);
    }

    @Override
    public void deleteById(BarId id) {
        jpaBarRepository.deleteById(id);
    }

    private boolean existsByNameAndDifferentId(Name name, BarId id) {
        return jpaBarRepository.existsByNameAndIdNot(name.getName(), id.getId());
    }

    public List<Bar> findByNameContaining(String name) {
        return findByNameContaining(new Name(name));
    }
}

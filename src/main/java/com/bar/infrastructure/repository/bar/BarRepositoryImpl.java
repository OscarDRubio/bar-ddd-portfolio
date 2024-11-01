package com.bar.infrastructure.repository.bar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.shared.Name;
import com.bar.domain.shared.Pagination;
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
    public Optional<Bar> findById(BarId id) {
        return jpaBarRepository.findById(id);
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
    public Bar save(Bar bar) {
        return jpaBarRepository.save(bar);
    }

    @Override
    public void deleteById(BarId id) {
        jpaBarRepository.deleteById(id);
    }

    @Override
    public boolean existsByNameAndDifferentId(Name name, BarId id) {
        return jpaBarRepository.existsByNameAndIdNot(name.getName(), id.getId());
    }

    public List<Bar> findByNameContaining(String name) {
        return findByNameContaining(new Name(name));
    }
}

package com.bar.infrastructure.repository.bartable;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bar.domain.bar.BarId;
import com.bar.domain.table.BarTable;
import com.bar.domain.table.IBarTableRepository;

@Repository
public class BarTableRepository implements IBarTableRepository {

    private final JpaBarTableRepository jpaBarTableRepository;

    @Autowired
    public BarTableRepository(JpaBarTableRepository jpaBarTableRepository) {
        this.jpaBarTableRepository = jpaBarTableRepository;
    }

    @Override
    public BarTable save(BarTable barTable) {
        return jpaBarTableRepository.save(barTable);
    }

    @Override
    public boolean existsByNameAndBarId(BarTable barTable) {
        return jpaBarTableRepository.existsByNameAndBarId(barTable.getName(), barTable.getBarId());
    }

    @Override
    public List<BarTable> findByBarId(BarId barId) {
        return jpaBarTableRepository.findByBarId(barId);
    }
}

package com.bar.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bar.domain.bar.BarId;
import com.bar.domain.shared.Name;
import com.bar.domain.table.BarTable;
import com.bar.domain.table.BarTableId;

@Repository
public interface JpaBarTableRepository extends JpaRepository<BarTable, BarTableId> {
    boolean existsByNameAndBarId(Name name, BarId id);
    List<BarTable> findByBarId(BarId barId);
}

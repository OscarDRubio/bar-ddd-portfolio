package com.bar.domain.table;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bar.domain.bar.BarId;
import com.bar.domain.shared.Name;

public interface IBarTableRepository extends JpaRepository<BarTable, BarTableId> {
    Optional<BarTable> findByNameAndBarId(Name name, BarId barId);
    List<BarTable> findByBarId(BarId barId);
}

package com.bar.infrastructure.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bar.domain.bar.BarId;
import com.bar.domain.table.BarTable;
import com.bar.domain.table.BarTableId;

@Repository
public interface BarTableRepository extends JpaRepository<BarTable, BarTableId> {
    
    List<BarTable> findByBarId(BarId barId);
}

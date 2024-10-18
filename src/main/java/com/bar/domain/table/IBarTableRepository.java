package com.bar.domain.table;

import java.util.List;
import com.bar.domain.bar.BarId;

public interface IBarTableRepository {
    BarTable save(BarTable barTable);
    boolean existsByNameAndBarId(BarTable barTable);
    List<BarTable> findByBarId(BarId barId);
}

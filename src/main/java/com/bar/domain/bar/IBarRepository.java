package com.bar.domain.bar;

import java.util.List;
import java.util.Optional;

import com.bar.domain.shared.Name;
import com.bar.domain.shared.Pagination;

public interface IBarRepository {

    Optional<Bar> findById(BarId id);
    List<Bar> findAllPaginated(Pagination pagination);
    List<Bar> findByNameContaining(Name name);
    Optional<Bar> findByName(Name name);
    boolean existsByNameAndDifferentId(Name name, BarId id);
    Bar save(Bar bar);
    void deleteById(BarId id);
}

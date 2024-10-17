package com.bar.infrastructure.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bar.domain.bar.BarId;
import com.bar.domain.table.BarTable;
import com.bar.infrastructure.repository.BarTableRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "BarTable", description = "Bar Tables management API")
@RestController
@RequestMapping("/api/bartable")
public class BarTableController {

    private final BarTableRepository barTableRepository;

    @Autowired
    public BarTableController(BarTableRepository barTableRepository) {
        this.barTableRepository = barTableRepository;
    }

    @GetMapping("/bar/{barId}")
    public ResponseEntity<List<BarTable>> getAllByByBarId(@PathVariable String barId) {
        return ResponseEntity.ok().body(barTableRepository.findByBarId(new BarId(barId)));
    }
}

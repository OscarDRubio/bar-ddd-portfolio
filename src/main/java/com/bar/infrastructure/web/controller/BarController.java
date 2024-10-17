package com.bar.infrastructure.web.controller;

import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.exception.NullNameException;
import com.bar.domain.shared.Name;
import com.bar.domain.table.BarTable;
import com.bar.infrastructure.repository.BarRepository;
import com.bar.infrastructure.repository.BarTableRepository;
import com.bar.infrastructure.web.controller.dto.CreateBarRequest;
import com.bar.infrastructure.web.controller.dto.CreateBarTableRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Bar", description = "Bar management API")
@RestController
@RequestMapping("/api/bar")
public class BarController {

    private final BarRepository barRepository;
    private final BarTableRepository barTableRepository;

    @Autowired
    public BarController(BarRepository barRepository, BarTableRepository barTableRepository) {
        this.barRepository = barRepository;
        this.barTableRepository = barTableRepository;
    }

    @Operation(
            summary = "Retrieve a list of bars with pagination",
            description = "Get a list of bar objects paginated. The response is a Bar list object.",
            tags = {"get", "list", "pageable" })
    @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())
    })
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Page.class),
                    mediaType = "application/json")
    })
    @GetMapping
    public ResponseEntity<Page<Bar>> getAllBars(Pageable pageable) {
        Page<Bar> barsPage = barRepository.findAll(pageable);
        return ResponseEntity.ok(barsPage);
    }

    @Operation(
            summary = "Retrieve a list of bars with pagination that contains a certain String in their name",
            description = "Get a list of bar objects paginated. The response is a Bar list object.",
            tags = {"get", "list", "pageable" })
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Page.class),
                    mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())
    })
    @Cacheable("barListByNameCache")
    @GetMapping("/search")
    public ResponseEntity<Page<Bar>> searchBarByName(@RequestParam String keyword, Pageable pageable) {
        Page<Bar> barsPage = barRepository.findByNameContaining(keyword, pageable);
        return ResponseEntity.ok(barsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bar> getBarById(@PathVariable String id) {
        Optional<Bar> barOptional = barRepository.findById(new BarId(id));
        return barOptional.map(bar -> ResponseEntity.ok().body(bar))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Bar> createBar(@RequestBody CreateBarRequest barRequest)
            throws DuplicateBarException, NullNameException {

        Bar bar = new Bar(
                new Name(barRequest.getName()));
        barRepository.create(bar);
        return ResponseEntity.status(HttpStatus.CREATED).body(bar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bar> updateBar(@PathVariable BarId id, @RequestBody CreateBarRequest barDTO)
            throws NullNameException, DuplicateBarException {

        Optional<Bar> barOptional = barRepository.findById(id);
        Bar bar = barOptional.get();
        bar.setName(new Name(barDTO.getName()));
        barRepository.update(bar);
        return ResponseEntity.ok().body(bar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBar(@PathVariable BarId id) {
        barRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/createTable")
    public ResponseEntity<BarTable> createBarTable(@PathVariable String id, @RequestBody CreateBarTableRequest request) throws DuplicateBarException {

        barRepository.findById(new BarId(id))
            .orElseThrow(() -> new EntityNotFoundException("Bar not found"));
        
        BarTable barTable = barTableRepository.create(
            new BarTable(
                new Name(request.getName()), 
                new BarId(id)));
        return ResponseEntity.status(HttpStatus.CREATED).body(barTable);
    }
}

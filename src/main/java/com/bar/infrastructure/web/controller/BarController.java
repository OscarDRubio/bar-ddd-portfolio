package com.bar.infrastructure.web.controller;

import com.bar.domain.bar.Bar;
import com.bar.domain.exception.DuplicateBarException;
import com.bar.domain.exception.NullNameException;
import com.bar.domain.shared.Name;
import com.bar.infrastructure.repository.bar.BarRepository;
import com.bar.infrastructure.web.controller.dto.BarRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Autowired
    public BarController(BarRepository barRepository) {
        this.barRepository = barRepository;
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
        Optional<Bar> barOptional = barRepository.findById(id);
        return barOptional.map(bar -> ResponseEntity.ok().body(bar))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Bar> createBar(@RequestBody BarRequest barRequest)
            throws DuplicateBarException, NullNameException {

        Bar bar = new Bar(
                new Name(barRequest.getName()));
        barRepository.create(barRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(bar);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateBar(@PathVariable String id, @RequestBody BarRequest barRequest) {

        barRepository.update(id, barRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBar(@PathVariable String id) {
        barRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //TODO: Create BarTable
    /**
    @PostMapping("/{id}/createTable")
    public ResponseEntity<BarTable> createBarTable(@PathVariable String id, @RequestBody CreateBarTableRequest request) {

        BarTable barTable = barRepository.createBarTable(
            new BarTable(
                new Name(request.getName()), 
                new BarId(id)));
        return ResponseEntity.status(HttpStatus.CREATED).body(barTable);
    }
    **/
}

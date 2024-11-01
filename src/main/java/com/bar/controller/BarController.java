package com.bar.controller;

import com.bar.application.bar.BarService;
import com.bar.application.bar.dto.BarRequest;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarDto;
import com.bar.domain.bar.BarId;
import com.bar.domain.bar.IBarRepository;

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

import java.util.List;
import java.util.Optional;

@Tag(name = "Bar", description = "Bar management API")
@RestController
@RequestMapping("/api/bar")
public class BarController {

    private final BarService barService;
    private final IBarRepository barRepository;

    @Autowired
    public BarController(BarService barService, IBarRepository barRepository) {
        this.barService = barService;
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
    public ResponseEntity<List<Bar>> getAllBars(Pageable pageable) {
        List<Bar> barsPage = barService.findAllPaginated(pageable.getPageNumber(), pageable.getPageSize());
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
    public ResponseEntity<List<Bar>> searchBarByName(@RequestParam String keyword) {
        List<Bar> barsPage = barService.findByNameContaining(keyword);
        return ResponseEntity.ok(barsPage);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Bar> getBarById(@PathVariable String id) {

    //TODO: Add Domain to HTTP error mapping

    //     Bar bar = barRepository.findById(new BarId(id));
    //     return bar.map(bar -> ResponseEntity.ok().body(bar))
    //             .orElseGet(() -> ResponseEntity.notFound().build());
    // }

    @PostMapping
    public ResponseEntity<BarDto> createBar(@RequestBody BarRequest barRequest) {

        BarDto result = barService.createBar(barRequest);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(result);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateBar(@RequestParam @PathVariable String id, @RequestBody BarRequest barRequest) {

        barService.updateBar(id, barRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBar(@PathVariable String id) {
        barService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //TODO: Create BarTable
    /**
    @PostMapping("/{id}/createTable")
    public ResponseEntity<BarTable> createBarTable(@PathVariable String id, @RequestBody CreateBarTableRequest request) {

        BarTable barTable = barService.createBarTable(
            new BarTable(
                new Name(request.getName()), 
                new BarId(id)));
        return ResponseEntity.status(HttpStatus.CREATED).body(barTable);
    }
    **/
}

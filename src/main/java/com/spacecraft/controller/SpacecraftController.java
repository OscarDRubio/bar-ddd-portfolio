package com.spacecraft.controller;

import com.spacecraft.domain.spacecraft.Spacecraft;
import com.spacecraft.domain.spacecraft.SpacecraftId;
import com.spacecraft.dto.SpacecraftDTO;
import com.spacecraft.exception.DuplicateSpacecraftException;
import com.spacecraft.exception.NullNameException;
import com.spacecraft.infrastructure.SpacecraftRepository;

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

import java.text.MessageFormat;
import java.util.Optional;

@Tag(name = "Spacecraft", description = "Spacecraft management API")
@RestController
@RequestMapping("/api/spacecraft")
public class SpacecraftController {

    private final SpacecraftRepository spacecraftRepository;
    @Autowired
    public SpacecraftController(SpacecraftRepository spacecraftRepository) {
        this.spacecraftRepository = spacecraftRepository;
    }

    @Operation(
            summary = "Retrieve a list of spacecrafts with pagination",
            description = "Get a list of spaceship objects paginated. The response is a Spacecraft list object.",
            tags = {"get", "list", "pageable" })
    @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())
    })
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Page.class),
                    mediaType = "application/json")
    })
    @GetMapping
    public ResponseEntity<Page<Spacecraft>> getAllSpacecrafts(Pageable pageable) {
        Page<Spacecraft> spacecraftsPage = spacecraftRepository.findAll(pageable);
        return ResponseEntity.ok(spacecraftsPage);
    }

    @Operation(
            summary = "Retrieve a list of spacecrafts with pagination that contains a certain String in their name",
            description = "Get a list of spaceship objects paginated. The response is a Spacecraft list object.",
            tags = {"get", "list", "pageable" })
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Page.class),
                    mediaType = "application/json")
    })
    @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())
    })
    @Cacheable("spacecraftListByNameCache")
    @GetMapping("/search")
    public ResponseEntity<Page<Spacecraft>> searchSpacecraftByName(@RequestParam String keyword, Pageable pageable) {
        Page<Spacecraft> spacecraftsPage = spacecraftRepository.findByNameContaining(keyword, pageable);
        return ResponseEntity.ok(spacecraftsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spacecraft> getSpacecraftById(@PathVariable SpacecraftId id) {
        Optional<Spacecraft> spacecraftOptional = spacecraftRepository.findById(id);
        return spacecraftOptional.map(spacecraft -> ResponseEntity.ok().body(spacecraft))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Spacecraft> createSpacecraft(@RequestBody SpacecraftDTO spacecraftDTO)
            throws DuplicateSpacecraftException, NullNameException {

        Spacecraft spacecraft = new Spacecraft(
                spacecraftDTO.getName());
        spacecraftRepository.save(spacecraft);
        return ResponseEntity.status(HttpStatus.CREATED).body(spacecraft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spacecraft> updateSpacecraft(@PathVariable SpacecraftId id, @RequestBody SpacecraftDTO spacecraftDTO)
            throws NullNameException {

        if(spacecraftDTO.getName() == null || spacecraftDTO.getName().isEmpty()) {
            throw new NullNameException(
                    MessageFormat.format("The spacecraft {0} cannot be null.", spacecraftDTO.getName()));
        }

        Optional<Spacecraft> spacecraftOptional = spacecraftRepository.findById(id);
        if (spacecraftOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Spacecraft spacecraft = spacecraftOptional.get();
        spacecraft.setName(spacecraftDTO.getName());
        spacecraftRepository.save(spacecraft);
        return ResponseEntity.ok().body(spacecraft);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSpacecraft(@PathVariable SpacecraftId id) {
        spacecraftRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

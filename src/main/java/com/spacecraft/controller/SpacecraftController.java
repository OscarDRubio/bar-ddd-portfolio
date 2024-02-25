package com.spacecraft.controller;

import com.spacecraft.exception.DuplicateSpacecraftException;
import com.spacecraft.exception.NullNameException;
import com.spacecraft.model.Spacecraft;
import com.spacecraft.repository.SpacecraftRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Spacecraft", description = "Spacecraf management API")
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Page.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    public ResponseEntity<Page<Spacecraft>> getAllSpacecrafts(Pageable pageable) {
        Page<Spacecraft> spacecraftsPage = spacecraftRepository.findAll(pageable);
        return ResponseEntity.ok(spacecraftsPage);
    }

    @Operation(
            summary = "Retrieve a list of spacecrafts with pagination that contains a certain String in their name",
            description = "Get a list of spaceship objects paginated. The response is a Spacecraft list object.",
            tags = {"get", "list", "pageable" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Page.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @Cacheable("spacecraftListByNameCache")
    @GetMapping("/search")
    public ResponseEntity<Page<Spacecraft>> searchSpacecraftByName(@RequestParam String keyword, Pageable pageable) {
        Page<Spacecraft> spacecraftsPage = spacecraftRepository.findByNameContaining(keyword, pageable);
        return ResponseEntity.ok(spacecraftsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spacecraft> getSpacecraftById(@PathVariable Long id) {
        Optional<Spacecraft> spacecraftOptional = spacecraftRepository.findById(id);
        return spacecraftOptional.map(spacecraft -> ResponseEntity.ok().body(spacecraft))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Spacecraft> createSpacecraft(@RequestBody Spacecraft spacecraft) throws DuplicateSpacecraftException, NullNameException {
        if(spacecraft.getName() == null || spacecraft.getName().isEmpty()) {
            throw new NullNameException("The spacecraft " + spacecraft.getName() + " cannot be null.");
        }
        if(spacecraftRepository.existsByName(spacecraft.getName())) {
            throw new DuplicateSpacecraftException("The spacecraft " + spacecraft.getName() + " already exists.");
        }
        Spacecraft savedSpacecraft = spacecraftRepository.save(spacecraft);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSpacecraft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spacecraft> updateSpacecraft(@PathVariable Long id, @RequestBody Spacecraft spacecraftDetails) throws NullNameException {
        if(spacecraftDetails.getName() == null || spacecraftDetails.getName().isEmpty()) {
            throw new NullNameException("The spacecraft " + spacecraftDetails.getName() + " cannot be null.");
        }
        Optional<Spacecraft> spacecraftOptional = spacecraftRepository.findById(id);
        if (spacecraftOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Spacecraft spacecraft = spacecraftOptional.get();
        spacecraft.setName(spacecraftDetails.getName());
        Spacecraft updatedSpacecraft = spacecraftRepository.save(spacecraft);
        return ResponseEntity.ok().body(updatedSpacecraft);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpacecraft(@PathVariable Long id) {
        spacecraftRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

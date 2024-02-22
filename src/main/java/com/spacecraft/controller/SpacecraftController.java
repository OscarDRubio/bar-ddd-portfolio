package com.spacecraft.controller;

import com.spacecraft.exception.DuplicateSpacecraftException;
import com.spacecraft.exception.NullNameException;
import com.spacecraft.model.Spacecraft;
import com.spacecraft.repository.SpacecraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/spacecraft")
public class SpacecraftController {

    private final SpacecraftRepository spacecraftRepository;
    @Autowired
    public SpacecraftController(SpacecraftRepository spacecraftRepository) {
        this.spacecraftRepository = spacecraftRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Spacecraft>> getAllSpacecrafts(Pageable pageable) {
        Page<Spacecraft> spacecraftsPage = spacecraftRepository.findAll(pageable);
        return ResponseEntity.ok(spacecraftsPage);
    }

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

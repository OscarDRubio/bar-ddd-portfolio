package com.spacecraft.domain.spacecraft;

import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public record SpacecraftId(String id) {

    public SpacecraftId() {
        this(UUID.randomUUID().toString());
    }    

    @Override
    public String toString() {
        return id;
    }
}

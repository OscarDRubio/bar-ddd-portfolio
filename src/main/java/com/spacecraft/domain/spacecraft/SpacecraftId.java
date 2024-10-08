package com.spacecraft.domain.spacecraft;

import java.util.UUID;

import jakarta.persistence.Embeddable;

// TODO: (Ready) Use uuids, allow empty constructor as well as parsing strings

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

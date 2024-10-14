package com.bar.domain.shared;

import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public record UuidIdentifier(String id) {

    public UuidIdentifier() {
        this(UUID.randomUUID().toString());
    }    

    @Override
    public String toString() {
        return id;
    }
}

package com.bar.domain.bar;

import com.bar.domain.shared.UuidIdentifier;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Embeddable;

@Embeddable
public record BarId(String id) {

    public BarId() {
        this(new UuidIdentifier().toString());
    }

    @JsonValue
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

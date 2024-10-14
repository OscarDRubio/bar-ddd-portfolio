package com.bar.domain.table;

import com.bar.domain.shared.UuidIdentifier;

import jakarta.persistence.Embeddable;

@Embeddable
public record BarTableId(String id) {

    public BarTableId() {
        this(new UuidIdentifier().toString());
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

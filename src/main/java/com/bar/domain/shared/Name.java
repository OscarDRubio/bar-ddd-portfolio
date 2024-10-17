package com.bar.domain.shared;

import com.bar.domain.exception.EmptyNameException;
import com.bar.domain.exception.NullNameException;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Embeddable;

@Embeddable
public record Name(String name) {

    public Name {
        if (name == null) {
            throw new NullNameException();
        }
        if (name.length() == 0) {
            throw new EmptyNameException();
        }
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

package com.bar.domain.shared;

import com.bar.domain.exceptions.EmptyNameException;
import com.bar.domain.exceptions.NullNameException;

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

    @Override
    public String toString() {
        return name;
    }
}

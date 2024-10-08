package com.spacecraft.domain.spacecraft;

import com.spacecraft.domain.exceptions.EmptyNameException;
import com.spacecraft.domain.exceptions.NullNameException;

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

package com.spacecraft.domain.spacecraft;

import com.spacecraft.domain.exceptions.EmptyNameException;
import com.spacecraft.domain.exceptions.NullNameException;

public record Name(String value) {

    public Name {
        if (value == null) {
            throw new NullNameException();
        }
        if (value.length() == 0) {
            throw new EmptyNameException();
        }
    }

    @Override
    public String toString() {
        return value;
    }
}

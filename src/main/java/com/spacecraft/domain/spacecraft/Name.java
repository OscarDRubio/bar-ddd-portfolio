package com.spacecraft.domain.spacecraft;

public record Name(String value) {

    public Name
    {
        if (value == null)
        {
            throw new RuntimeException("Name cannot be null.");
        }
        if (value.length() == 0)
        {
            throw new RuntimeException("Name cannot be empty.");
        }
    }

    public String toString()
    {
        return value;
    }
}

package com.bar.domain.shared;

import java.time.ZonedDateTime;

public record DateTimeWithZone(ZonedDateTime dateTimeWithZone) {

    public DateTimeWithZone {
        if (dateTimeWithZone == null) {
            throw new IllegalArgumentException("DateTime cannot be null");
        }
    }

    @Override
    public String toString() {
        return dateTimeWithZone.toString();
    }
}

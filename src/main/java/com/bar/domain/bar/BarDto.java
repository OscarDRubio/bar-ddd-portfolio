package com.bar.domain.bar;

public record BarDto(String id, String name) {

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

package com.bar.application.bar.command;

public class UpdateBarCommand {

    private final String id;
    private final String name;

    public UpdateBarCommand(String id, String name) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id cannot be null or empty.");
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

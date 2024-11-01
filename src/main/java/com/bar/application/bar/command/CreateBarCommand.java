package com.bar.application.bar.command;

public class CreateBarCommand {

    private final String id;
    private final String name;

    public CreateBarCommand(String name) {

        this(null, name);
    }

    public CreateBarCommand(String id, String name) {

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

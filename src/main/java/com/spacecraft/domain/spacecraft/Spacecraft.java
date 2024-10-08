package com.spacecraft.domain.spacecraft;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spacecraft")
public class Spacecraft {

    @EmbeddedId
    private SpacecraftId id;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "name", unique = true, nullable = false))
    })
    private Name name;

    public Spacecraft() {
        this.id = new SpacecraftId();
    }

    public Spacecraft(String name) {
        this();
        this.name = new Name(name);
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = new SpacecraftId(id);
    }

    public String getName() {
        return name.toString();
    }

    public void setName(String name) {
        this.name = new Name(name);
    }
}
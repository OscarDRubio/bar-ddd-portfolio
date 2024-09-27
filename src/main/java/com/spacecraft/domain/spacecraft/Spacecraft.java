package com.spacecraft.domain.spacecraft;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spacecraft")
public class Spacecraft {

    @Id
    private SpacecraftId id;  // MongoDB usa un identificador de tipo String
    private String name;
    private Integer crew = 0;  // Valor por defecto para crew

    public Spacecraft() {
    }

    public Spacecraft(String name) {
        this.name = name;
    }

    public Spacecraft(String name, Integer crew) {
        this.name = name;
        this.crew = crew;
    }

    public SpacecraftId getId() {
        return id;
    }

    public void setId(SpacecraftId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCrew() {
        return crew;
    }

    public void setCrew(Integer crew) {
        this.crew = crew;
    }
}
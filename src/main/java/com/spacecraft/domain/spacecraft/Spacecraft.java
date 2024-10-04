package com.spacecraft.domain.spacecraft;

import com.spacecraft.domain.spacecraft.converter.SpacecraftIdConverter;

import jakarta.persistence.*;

@Entity
@Table(name="spacecraft", indexes = {@Index(name = "idx_name", columnList = "name")})
public class Spacecraft {

    @Id
    //@Convert(converter = SpacecraftIdConverter.class)
    private String id;
    //@Convert(converter = NameConverter.class)  <-- No need to use cause NameConverter have the AutoApply to true
    @Column(unique = true, nullable = false)
    private Name name;

    public Spacecraft() {
        this.id = new SpacecraftId().toString();
    }

    public Spacecraft(String name) {
        this();
        this.name = new Name(name);
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = new SpacecraftId(id).toString();
    }

    public String getName() {
        return name.toString();
    }

    public void setName(String name) {
        this.name = new Name(name);
    }
}

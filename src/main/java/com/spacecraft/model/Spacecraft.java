package com.spacecraft.model;

import jakarta.persistence.*;

@Entity
@Table(name="spacecraft", indexes = {@Index(name = "idx_name", columnList = "name")})
public class Spacecraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;

    public Spacecraft() {
    }

    public Spacecraft(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

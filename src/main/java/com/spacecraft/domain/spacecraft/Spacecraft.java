package com.spacecraft.domain.spacecraft;

import jakarta.persistence.*;

@Entity
@Table(name="spacecraft", indexes = {@Index(name = "idx_name", columnList = "name")})
public class Spacecraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false, columnDefinition = "int default 0")
    private Integer crew;

    public Spacecraft() {
    }

    public Spacecraft(String name) {
        this.name = name;
    }

    public Spacecraft(String name, Integer crew) {
        this.name = name;
        this.crew = crew;
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

    public Integer getCrew() {
        return crew;
    }

    public void setCrew(Integer crew) {
        this.crew = crew;
    }
}

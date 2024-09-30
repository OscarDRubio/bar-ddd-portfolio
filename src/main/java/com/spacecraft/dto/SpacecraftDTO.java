package com.spacecraft.dto;

public class SpacecraftDTO {

    private String name;
    private Integer crew;

    public SpacecraftDTO(String name, Integer crew) {
        this.name = name;
        this.crew = crew;
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

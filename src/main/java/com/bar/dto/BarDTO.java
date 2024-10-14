package com.bar.dto;

public class BarDTO {

    private String name;
    private Integer crew;

    public BarDTO(String name, Integer crew) {
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

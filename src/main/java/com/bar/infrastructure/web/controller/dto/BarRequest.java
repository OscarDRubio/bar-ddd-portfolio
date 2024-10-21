package com.bar.infrastructure.web.controller.dto;

public class BarRequest {

    private String name;

    public BarRequest() {
    }

    public BarRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
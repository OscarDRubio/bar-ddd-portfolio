package com.bar.infrastructure.web.controller.dto;

public class CreateBarRequest {

    private String name;

    public CreateBarRequest() {
    }

    public CreateBarRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
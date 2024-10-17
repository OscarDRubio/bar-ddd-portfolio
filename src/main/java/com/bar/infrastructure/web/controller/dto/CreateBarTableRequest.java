package com.bar.infrastructure.web.controller.dto;

public class CreateBarTableRequest {

    private String name;

    public CreateBarTableRequest() {}

    public CreateBarTableRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

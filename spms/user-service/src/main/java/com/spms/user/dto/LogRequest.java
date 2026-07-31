package com.spms.user.dto;

import jakarta.validation.constraints.NotBlank;

public class LogRequest {
    @NotBlank(message = "Description is required")
    private String description;

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}

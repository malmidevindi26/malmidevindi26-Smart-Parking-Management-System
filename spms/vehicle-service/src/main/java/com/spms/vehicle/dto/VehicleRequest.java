package com.spms.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleRequest {

    @NotBlank(message = "Plate number is required")
    private String plateNumber;

    private String make;
    private String model;
    private String color;

    @NotNull(message = "userId is required")
    private Long userId;

    public String getPlateNumber() {
        return plateNumber;
    }
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

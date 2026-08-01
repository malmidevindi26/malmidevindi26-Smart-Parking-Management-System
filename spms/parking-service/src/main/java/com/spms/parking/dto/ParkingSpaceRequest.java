package com.spms.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ParkingSpaceRequest {

    @NotBlank(message = "Space code is required")
    private String spaceCode;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Zone is required")
    private String zone;

    @NotNull(message = "ownerId is required")
    private Long ownerId;

    @Positive(message = "Hourly rate must be positive")
    private Double hourlyRate;

    public String getSpaceCode() {
        return spaceCode;
    }
    public void setSpaceCode(String spaceCode) {
        this.spaceCode = spaceCode;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getZone() {
        return zone;
    }
    public void setZone(String zone) {
        this.zone = zone;
    }
    public Long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    public Double getHourlyRate() {
        return hourlyRate;
    }
    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}

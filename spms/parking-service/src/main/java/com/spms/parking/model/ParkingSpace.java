package com.spms.parking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_spaces")
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String spaceCode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zone;

    @Column(nullable = false)
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    private SpaceStatus status = SpaceStatus.AVAILABLE;

    private Double hourlyRate;

    private Long reservedByUserId;
    private Long reservedByVehicleId;

    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    public ParkingSpace() {}

    public ParkingSpace(String spaceCode, String city, String zone, Long ownerId, Double hourlyRate) {
        this.spaceCode = spaceCode;
        this.city = city;
        this.zone = zone;
        this.ownerId = ownerId;
        this.hourlyRate = hourlyRate;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public SpaceStatus getStatus() {
        return status;
    }
    public void setStatus(SpaceStatus status) {
        this.status = status;
    }
    public Double getHourlyRate() {
        return hourlyRate;
    }
    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    public Long getReservedByUserId() {
        return reservedByUserId;
    }
    public void setReservedByUserId(Long reservedByUserId) {
        this.reservedByUserId = reservedByUserId;
    }
    public Long getReservedByVehicleId() {
        return reservedByVehicleId;
    }
    public void setReservedByVehicleId(Long reservedByVehicleId) {
        this.reservedByVehicleId = reservedByVehicleId;
    }
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

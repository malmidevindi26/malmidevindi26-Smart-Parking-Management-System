package com.spms.vehicle.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entry_exit_logs")
public class EntryExitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long vehicleId;

    @Enumerated(EnumType.STRING)
    private EntryExitStatus status;

    private String parkingSpaceCode;

    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    public EntryExitLog() {}

    public EntryExitLog(Long vehicleId, EntryExitStatus status, String parkingSpaceCode) {
        this.vehicleId = vehicleId;
        this.status = status;
        this.parkingSpaceCode = parkingSpaceCode;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    public EntryExitStatus getStatus() {
        return status;
    }
    public void setStatus(EntryExitStatus status) {
        this.status = status;
    }
    public String getParkingSpaceCode() {
        return parkingSpaceCode;
    }
    public void setParkingSpaceCode(String parkingSpaceCode) {
        this.parkingSpaceCode = parkingSpaceCode;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

package com.spms.parking.controller;

import com.spms.parking.dto.ParkingSpaceRequest;
import com.spms.parking.dto.ReservationRequest;
import com.spms.parking.dto.StatusUpdateRequest;
import com.spms.parking.model.ParkingSpace;
import com.spms.parking.service.ParkingSpaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @Autowired
    public ParkingSpaceController(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }

    @PostMapping
    public ResponseEntity<ParkingSpace> create(@Valid @RequestBody ParkingSpaceRequest request) {
        return new ResponseEntity<>(parkingSpaceService.createSpace(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpace>> getAll(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) Boolean availableOnly) {
        if (city == null && zone == null && availableOnly == null) {
            return ResponseEntity.ok(parkingSpaceService.getAll());
        }
        return ResponseEntity.ok(parkingSpaceService.filter(city, zone, availableOnly));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpace> getById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpaceService.getById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ParkingSpace>> getByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(parkingSpaceService.getByOwner(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpace> update(@PathVariable Long id, @Valid @RequestBody ParkingSpaceRequest request) {
        return ResponseEntity.ok(parkingSpaceService.updateSpace(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parkingSpaceService.deleteSpace(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reserve")
    public ResponseEntity<ParkingSpace> reserve(@PathVariable Long id, @Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(parkingSpaceService.reserve(id, request.getUserId(), request.getVehicleId()));
    }

    @PostMapping("/{id}/release")
    public ResponseEntity<ParkingSpace> release(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpaceService.release(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ParkingSpace> updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(parkingSpaceService.updateStatus(id, request.getStatus()));
    }
}

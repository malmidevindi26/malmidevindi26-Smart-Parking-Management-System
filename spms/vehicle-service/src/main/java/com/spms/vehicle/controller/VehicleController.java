package com.spms.vehicle.controller;

import com.spms.vehicle.dto.EntryExitRequest;
import com.spms.vehicle.dto.VehicleRequest;
import com.spms.vehicle.model.EntryExitLog;
import com.spms.vehicle.model.Vehicle;
import com.spms.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> register(@Valid @RequestBody VehicleRequest request) {
        return new ResponseEntity<>(vehicleService.registerVehicle(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAll() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Vehicle>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(vehicleService.getVehiclesByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/entry")
    public ResponseEntity<EntryExitLog> recordEntry(@PathVariable Long id, @RequestBody(required = false) EntryExitRequest request) {
        String code = request != null ? request.getParkingSpaceCode() : null;
        return new ResponseEntity<>(vehicleService.recordEntry(id, code), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/exit")
    public ResponseEntity<EntryExitLog> recordExit(@PathVariable Long id, @RequestBody(required = false) EntryExitRequest request) {
        String code = request != null ? request.getParkingSpaceCode() : null;
        return new ResponseEntity<>(vehicleService.recordExit(id, code), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/tracking")
    public ResponseEntity<List<EntryExitLog>> getTracking(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getTrackingHistory(id));
    }
}

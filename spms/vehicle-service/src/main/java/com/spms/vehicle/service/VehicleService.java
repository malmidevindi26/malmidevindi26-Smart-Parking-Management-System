package com.spms.vehicle.service;

import com.spms.vehicle.client.UserClient;
import com.spms.vehicle.dto.VehicleRequest;
import com.spms.vehicle.exception.DuplicateResourceException;
import com.spms.vehicle.exception.InvalidOperationException;
import com.spms.vehicle.exception.ResourceNotFoundException;
import com.spms.vehicle.model.EntryExitLog;
import com.spms.vehicle.model.EntryExitStatus;
import com.spms.vehicle.model.Vehicle;
import com.spms.vehicle.repository.EntryExitLogRepository;
import com.spms.vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final EntryExitLogRepository entryExitLogRepository;
    private final UserClient userClient;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository,
                           EntryExitLogRepository entryExitLogRepository,
                           UserClient userClient) {
        this.vehicleRepository = vehicleRepository;
        this.entryExitLogRepository = entryExitLogRepository;
        this.userClient = userClient;
    }

    public Vehicle registerVehicle(VehicleRequest request) {
        if (vehicleRepository.existsByPlateNumber(request.getPlateNumber())) {
            throw new DuplicateResourceException("Vehicle with plate number '" + request.getPlateNumber() + "' already registered");
        }
        if (!userClient.userExists(request.getUserId())) {
            throw new ResourceNotFoundException("No user found with id: " + request.getUserId());
        }
        Vehicle vehicle = new Vehicle(
                request.getPlateNumber(),
                request.getMake(),
                request.getModel(),
                request.getColor(),
                request.getUserId()
        );
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    public List<Vehicle> getVehiclesByUser(Long userId) {
        return vehicleRepository.findByUserId(userId);
    }

    public Vehicle updateVehicle(Long id, VehicleRequest request) {
        Vehicle vehicle = getVehicleById(id);
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setColor(request.getColor());
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = getVehicleById(id);
        vehicleRepository.delete(vehicle);
    }

    public EntryExitLog recordEntry(Long vehicleId, String parkingSpaceCode) {
        getVehicleById(vehicleId); // validate vehicle exists
        List<EntryExitLog> logs = entryExitLogRepository.findByVehicleIdOrderByTimestampDesc(vehicleId);
        if (!logs.isEmpty() && logs.get(0).getStatus() == EntryExitStatus.ENTERED) {
            throw new InvalidOperationException("Vehicle " + vehicleId + " is already recorded as inside the facility");
        }
        return entryExitLogRepository.save(new EntryExitLog(vehicleId, EntryExitStatus.ENTERED, parkingSpaceCode));
    }

    public EntryExitLog recordExit(Long vehicleId, String parkingSpaceCode) {
        getVehicleById(vehicleId);
        List<EntryExitLog> logs = entryExitLogRepository.findByVehicleIdOrderByTimestampDesc(vehicleId);
        if (logs.isEmpty() || logs.get(0).getStatus() == EntryExitStatus.EXITED) {
            throw new InvalidOperationException("Vehicle " + vehicleId + " has no active entry to exit from");
        }
        return entryExitLogRepository.save(new EntryExitLog(vehicleId, EntryExitStatus.EXITED, parkingSpaceCode));
    }

    public List<EntryExitLog> getTrackingHistory(Long vehicleId) {
        getVehicleById(vehicleId);
        return entryExitLogRepository.findByVehicleIdOrderByTimestampDesc(vehicleId);
    }
}

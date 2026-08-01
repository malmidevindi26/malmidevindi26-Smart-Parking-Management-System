package com.spms.parking.service;

import com.spms.parking.dto.ParkingSpaceRequest;
import com.spms.parking.exception.DuplicateResourceException;
import com.spms.parking.exception.InvalidOperationException;
import com.spms.parking.exception.ResourceNotFoundException;
import com.spms.parking.model.ParkingSpace;
import com.spms.parking.model.SpaceStatus;
import com.spms.parking.repository.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository repository;

    @Autowired
    public ParkingSpaceService(ParkingSpaceRepository repository) {
        this.repository = repository;
    }

    public ParkingSpace createSpace(ParkingSpaceRequest request) {
        if (repository.existsBySpaceCode(request.getSpaceCode())) {
            throw new DuplicateResourceException("Parking space with code '" + request.getSpaceCode() + "' already exists");
        }
        ParkingSpace space = new ParkingSpace(
                request.getSpaceCode(),
                request.getCity(),
                request.getZone(),
                request.getOwnerId(),
                request.getHourlyRate()
        );
        return repository.save(space);
    }

    public List<ParkingSpace> getAll() {
        return repository.findAll();
    }

    public ParkingSpace getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with id: " + id));
    }

    public List<ParkingSpace> getByOwner(Long ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    public List<ParkingSpace> filter(String city, String zone, Boolean availableOnly) {
        List<ParkingSpace> result;
        if (city != null && zone != null) {
            result = repository.findByCityIgnoreCaseAndZoneIgnoreCase(city, zone);
        } else if (city != null && Boolean.TRUE.equals(availableOnly)) {
            result = repository.findByCityIgnoreCaseAndStatus(city, SpaceStatus.AVAILABLE);
        } else if (city != null) {
            result = repository.findByCityIgnoreCase(city);
        } else if (Boolean.TRUE.equals(availableOnly)) {
            result = repository.findByStatus(SpaceStatus.AVAILABLE);
        } else {
            result = repository.findAll();
        }
        return result;
    }

    public ParkingSpace updateSpace(Long id, ParkingSpaceRequest request) {
        ParkingSpace space = getById(id);
        space.setCity(request.getCity());
        space.setZone(request.getZone());
        space.setHourlyRate(request.getHourlyRate());
        return repository.save(space);
    }

    public void deleteSpace(Long id) {
        ParkingSpace space = getById(id);
        repository.delete(space);
    }

    public ParkingSpace reserve(Long id, Long userId, Long vehicleId) {
        ParkingSpace space = getById(id);
        if (space.getStatus() != SpaceStatus.AVAILABLE) {
            throw new InvalidOperationException("Parking space " + space.getSpaceCode() + " is not available (current status: " + space.getStatus() + ")");
        }
        space.setStatus(SpaceStatus.RESERVED);
        space.setReservedByUserId(userId);
        space.setReservedByVehicleId(vehicleId);
        return repository.save(space);
    }

    public ParkingSpace release(Long id) {
        ParkingSpace space = getById(id);
        if (space.getStatus() == SpaceStatus.AVAILABLE) {
            throw new InvalidOperationException("Parking space " + space.getSpaceCode() + " is already available");
        }
        space.setStatus(SpaceStatus.AVAILABLE);
        space.setReservedByUserId(null);
        space.setReservedByVehicleId(null);
        return repository.save(space);
    }

    public ParkingSpace updateStatus(Long id, SpaceStatus status) {
        ParkingSpace space = getById(id);
        space.setStatus(status);
        if (status == SpaceStatus.AVAILABLE) {
            space.setReservedByUserId(null);
            space.setReservedByVehicleId(null);
        }
        return repository.save(space);
    }
}

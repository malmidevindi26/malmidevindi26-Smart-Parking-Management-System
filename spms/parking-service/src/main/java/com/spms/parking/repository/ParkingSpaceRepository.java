package com.spms.parking.repository;

import com.spms.parking.model.ParkingSpace;
import com.spms.parking.model.SpaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    Optional<ParkingSpace> findBySpaceCode(String spaceCode);
    boolean existsBySpaceCode(String spaceCode);
    List<ParkingSpace> findByOwnerId(Long ownerId);
    List<ParkingSpace> findByCityIgnoreCase(String city);
    List<ParkingSpace> findByStatus(SpaceStatus status);
    List<ParkingSpace> findByCityIgnoreCaseAndZoneIgnoreCase(String city, String zone);
    List<ParkingSpace> findByCityIgnoreCaseAndStatus(String city, SpaceStatus status);
}

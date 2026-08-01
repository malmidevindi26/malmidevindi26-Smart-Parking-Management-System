package com.spms.vehicle.repository;

import com.spms.vehicle.model.EntryExitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryExitLogRepository extends JpaRepository<EntryExitLog, Long> {
    List<EntryExitLog> findByVehicleIdOrderByTimestampDesc(Long vehicleId);
}

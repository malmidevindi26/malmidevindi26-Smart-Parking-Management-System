package com.spms.parking.dto;

import com.spms.parking.model.SpaceStatus;
import jakarta.validation.constraints.NotNull;
public class StatusUpdateRequest {

    @NotNull(message = "status is required")
    private SpaceStatus status;

    public SpaceStatus getStatus() {
        return status;
    }
    public void setStatus(SpaceStatus status) {
        this.status = status;
    }
}

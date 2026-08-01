package com.spms.payment.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class ParkingClient {

    private final RestTemplate restTemplate;

    @Autowired
    public ParkingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void releaseSpace(Long parkingSpaceId) {
        try {
            restTemplate.postForObject(
                    "http://parking-service/api/parking/" + parkingSpaceId + "/release",
                    null,
                    Object.class);
        } catch (Exception ex) {
            System.err.println("Warning: could not release parking space " + parkingSpaceId + ": " + ex.getMessage());
        }
    }
}

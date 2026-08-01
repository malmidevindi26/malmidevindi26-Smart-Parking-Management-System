package com.spms.vehicle.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
@Component
public class UserClient {

    private final RestTemplate restTemplate;

    @Autowired
    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean userExists(Long userId) {
        try {
            restTemplate.getForObject("http://user-service/api/users/" + userId, Object.class);
            return true;
        } catch (HttpClientErrorException.NotFound ex) {
            return false;
        } catch (Exception ex) {
            System.err.println("Warning" + userId + ": " + ex.getMessage());
            return true;
        }
    }
}

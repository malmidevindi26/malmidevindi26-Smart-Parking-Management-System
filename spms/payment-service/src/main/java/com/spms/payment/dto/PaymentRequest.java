package com.spms.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "parkingSpaceId is required")
    private Long parkingSpaceId;

    private Long vehicleId;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be positive")
    private Double amount;

    @NotBlank(message = "cardNumber is required")
    private String cardNumber;

    @NotBlank(message = "expiryDate is required")
    private String expiryDate;

    @NotBlank(message = "cvv is required")
    private String cvv;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getParkingSpaceId() {
        return parkingSpaceId;
    }
    public void setParkingSpaceId(Long parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }
    public Long getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}

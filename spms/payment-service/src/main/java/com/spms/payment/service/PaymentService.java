package com.spms.payment.service;

import com.spms.payment.client.ParkingClient;
import com.spms.payment.dto.PaymentRequest;
import com.spms.payment.exception.PaymentDeclinedException;
import com.spms.payment.exception.ResourceNotFoundException;
import com.spms.payment.model.Payment;
import com.spms.payment.model.PaymentStatus;
import com.spms.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class PaymentService {

    private static final Pattern CARD_PATTERN = Pattern.compile("^\\d{16}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^\\d{3,4}$");

    private final PaymentRepository paymentRepository;
    private final ParkingClient parkingClient;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ParkingClient parkingClient) {
        this.paymentRepository = paymentRepository;
        this.parkingClient = parkingClient;
    }

    public Payment processPayment(PaymentRequest request) {
        validateCardData(request);

        String masked = maskCard(request.getCardNumber());
        Payment payment = new Payment(
                request.getUserId(),
                request.getParkingSpaceId(),
                request.getVehicleId(),
                request.getAmount(),
                masked
        );

        if (request.getCardNumber().startsWith("0000")) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new PaymentDeclinedException("Payment declined by mock gateway for card ending in "
                    + masked.substring(masked.length() - 4));
        }

        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setReceiptNumber("RCPT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        Payment saved = paymentRepository.save(payment);

        parkingClient.releaseSpace(request.getParkingSpaceId());

        return saved;
    }

    private void validateCardData(PaymentRequest request) {
        if (!CARD_PATTERN.matcher(request.getCardNumber()).matches()) {
            throw new PaymentDeclinedException("Invalid card number format: must be exactly 16 digits");
        }
        if (!CVV_PATTERN.matcher(request.getCvv()).matches()) {
            throw new PaymentDeclinedException("Invalid CVV format");
        }
        if (!request.getExpiryDate().matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            throw new PaymentDeclinedException("Invalid expiry date format, expected MM/YY");
        }
    }

    private String maskCard(String cardNumber) {
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Payment getById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    public List<Payment> getByUser(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    public Payment getReceipt(String receiptNumber) {
        return paymentRepository.findByReceiptNumber(receiptNumber)
                .orElseThrow(() -> new ResourceNotFoundException("No receipt found: " + receiptNumber));
    }
}

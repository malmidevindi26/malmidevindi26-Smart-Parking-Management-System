package com.spms.payment.controller;

import com.spms.payment.dto.PaymentRequest;
import com.spms.payment.model.Payment;
import com.spms.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> process(@Valid @RequestBody PaymentRequest request) {
        return new ResponseEntity<>(paymentService.processPayment(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(paymentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getByUser(userId));
    }

    @GetMapping("/receipt/{receiptNumber}")
    public ResponseEntity<Payment> getReceipt(@PathVariable String receiptNumber) {
        return ResponseEntity.ok(paymentService.getReceipt(receiptNumber));
    }
}

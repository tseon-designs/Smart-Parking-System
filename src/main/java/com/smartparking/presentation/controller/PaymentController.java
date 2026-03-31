package com.smartparking.presentation.controller;

import com.smartparking.application.dto.PaymentDto;
import com.smartparking.application.usecase.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @PostMapping
    public ResponseEntity<PaymentDto> processPayment(@RequestBody PaymentDto dto) {
        return new ResponseEntity<>(paymentUseCase.processPayment(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentUseCase.getPayment(paymentId));
    }
}
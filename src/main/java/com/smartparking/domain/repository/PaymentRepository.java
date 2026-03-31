package com.smartparking.domain.repository;

import com.smartparking.domain.model.Payment;

import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(Long paymentId);

    Payment save(Payment payment);
}
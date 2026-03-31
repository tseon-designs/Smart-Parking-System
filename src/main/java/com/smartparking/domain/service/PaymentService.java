package com.smartparking.domain.service;

import com.smartparking.domain.model.Payment;
import com.smartparking.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment processPayment(Payment payment) {
        payment.setStatus("COMPLETED");
        payment.setPaymentTime(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment findById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));
    }
}
package com.smartparking.application.usecase;

import com.smartparking.application.dto.PaymentDto;
import com.smartparking.domain.model.Payment;
import com.smartparking.domain.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentUseCase {

    private final PaymentService paymentService;

    public PaymentDto processPayment(PaymentDto dto) {
        Payment payment = toDomain(dto);
        Payment processed = paymentService.processPayment(payment);
        return toDto(processed);
    }

    public PaymentDto getPayment(Long paymentId) {
        Payment payment = paymentService.findById(paymentId);
        return toDto(payment);
    }

    private PaymentDto toDto(Payment payment) {
        return new PaymentDto(
                payment.getPaymentId(),
                payment.getReservationId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getPaymentTime());
    }

    private Payment toDomain(PaymentDto dto) {
        return new Payment(
                dto.getPaymentId(),
                dto.getReservationId(),
                dto.getAmount(),
                dto.getPaymentMethod(),
                dto.getStatus(),
                dto.getPaymentTime());
    }
}
package com.smartparking.infrastructure.persistence.repository;

import com.smartparking.domain.model.Payment;
import com.smartparking.domain.repository.PaymentRepository;
import com.smartparking.infrastructure.persistence.entity.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

interface SpringDataPaymentRepository extends JpaRepository<PaymentEntity, Long> {
}

@Repository
@RequiredArgsConstructor
public class JpaPaymentRepository implements PaymentRepository {

    private final SpringDataPaymentRepository springRepo;

    @Override
    public Optional<Payment> findById(Long paymentId) {
        return springRepo.findById(paymentId).map(this::toDomain);
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = toEntity(payment);
        PaymentEntity saved = springRepo.save(entity);
        return toDomain(saved);
    }

    private Payment toDomain(PaymentEntity entity) {
        return new Payment(
                entity.getPaymentId(),
                entity.getReservationId(),
                entity.getAmount(),
                entity.getPaymentMethod(),
                entity.getStatus(),
                entity.getPaymentTime());
    }

    private PaymentEntity toEntity(Payment payment) {
        return new PaymentEntity(
                payment.getPaymentId(),
                payment.getReservationId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getPaymentTime());
    }
}
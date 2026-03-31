package com.smartparking.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_reservation", columnList = "reservation_id"),
        @Index(name = "idx_payment_status", columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    @Column(name = "transaction_reference", length = 100)
    private String transactionReference;

    @Column(name = "receipt_number", length = 50)
    private String receiptNumber;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor matching domain model fields (adapted for Long ID)
    public PaymentEntity(Long paymentId, Long reservationId, BigDecimal amount,
                          String paymentMethod, String status, LocalDateTime paymentTime) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentTime = paymentTime;
    }
}
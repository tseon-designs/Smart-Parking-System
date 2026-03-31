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
@Table(name = "reservations", indexes = {
        @Index(name = "idx_res_spot_status", columnList = "spot_id, status"),
        @Index(name = "idx_res_license", columnList = "vehicle_license_plate"),
        @Index(name = "idx_res_start_time", columnList = "start_time")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "spot_id", nullable = false)
    private Long spotId;

    @Column(name = "vehicle_license_plate", nullable = false, length = 20)
    private String vehicleLicensePlate;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Column(name = "qr_code_data", length = 500)
    private String qrCodeData;

    @Column(name = "notes", length = 500)
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor matching domain model fields (adapted for Long ID)
    public ReservationEntity(Long reservationId, Long spotId, String vehicleLicensePlate,
                              LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.reservationId = reservationId;
        this.spotId = spotId;
        this.vehicleLicensePlate = vehicleLicensePlate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }
}
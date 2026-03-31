package com.smartparking.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles", indexes = {
        @Index(name = "idx_vehicle_owner", columnList = "owner_name"),
        @Index(name = "idx_vehicle_type", columnList = "vehicle_type")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Column(name = "license_plate", nullable = false, unique = true, length = 20)
    private String licensePlate;

    @Column(name = "vehicle_type", nullable = false, length = 30)
    private String vehicleType;    // CAR, MOTORCYCLE, TRUCK, VAN, BUS

    @Column(name = "owner_name", nullable = false, length = 150)
    private String ownerName;

    @Column(name = "owner_email", length = 200)
    private String ownerEmail;

    @Column(name = "owner_phone", length = 20)
    private String ownerPhone;

    @Column(name = "make", length = 60)
    private String make;

    @Column(name = "model", length = 60)
    private String model;

    @Column(name = "color", length = 40)
    private String color;

    @Column(name = "year")
    private Integer year;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor matching domain model fields
    public VehicleEntity(Long vehicleId, String licensePlate, String vehicleType, String ownerName) {
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.ownerName = ownerName;
    }
}

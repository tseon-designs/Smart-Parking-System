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
@Table(name = "parking_spots", indexes = {
        @Index(name = "idx_spot_lot", columnList = "parking_lot_id"),
        @Index(name = "idx_spot_occupied", columnList = "occupied")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_id")
    private Long spotId;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    @Column(name = "price_per_hour", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLotEntity parkingLot;

    @Column(name = "spot_type", length = 30)
    @Enumerated(EnumType.STRING)
    private SpotType spotType = SpotType.STANDARD;

    @Column(name = "floor_level")
    private Integer floorLevel = 1;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor matching original needs (adapted for Long ID)
    public ParkingSpotEntity(Long spotId, String location, boolean occupied,
                              BigDecimal pricePerHour, ParkingLotEntity parkingLot) {
        this.spotId = spotId;
        this.location = location;
        this.occupied = occupied;
        this.pricePerHour = pricePerHour;
        this.parkingLot = parkingLot;
    }


    public enum SpotType {
        STANDARD, COMPACT, HANDICAPPED, EV_CHARGING, OVERSIZED
    }
}
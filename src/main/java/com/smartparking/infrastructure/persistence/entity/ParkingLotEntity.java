package com.smartparking.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_lots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_lot_id")
    private Long parkingLotId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "total_spots", nullable = false)
    private int totalSpots;

    @Column(name = "address", length = 300)
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ParkingSpotEntity> spots = new ArrayList<>();

    // Constructor for minimal creation
    public ParkingLotEntity(Long parkingLotId, String name, int totalSpots) {
        this.parkingLotId = parkingLotId;
        this.name = name;
        this.totalSpots = totalSpots;
    }
}
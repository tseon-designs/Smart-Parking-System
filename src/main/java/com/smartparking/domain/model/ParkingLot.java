package com.smartparking.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Aggregate root representing a parking lot.
 * Controls access to all its parking spots and enforces business rules.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {
    private Long parkingLotId;
    private String name;
    private int totalSpots;
    private List<ParkingSpot> spots = new ArrayList<>();

    public ParkingLot(Long parkingLotId, String name, int totalSpots) {
        this.parkingLotId = parkingLotId;
        this.name = name;
        this.totalSpots = totalSpots;
    }

    /**
     * Business rule: A spot can only be occupied if it exists and is not already occupied.
     */
    public void occupySpot(Long spotId) {
        ParkingSpot spot = findSpot(spotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking spot not found: " + spotId));
        
        if (spot.isOccupied()) {
            throw new IllegalStateException("Spot " + spotId + " is already occupied.");
        }
        
        spot.occupy();
    }

    /**
     * Business rule: Releasing a spot marks it as available.
     */
    public void releaseSpot(Long spotId) {
        ParkingSpot spot = findSpot(spotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking spot not found: " + spotId));
        
        spot.release();
    }

    /**
     * Business rule: Adding a spot must not exceed the total capacity.
     */
    public void addParkingSpot(ParkingSpot spot) {
        if (spots.size() >= totalSpots) {
            throw new IllegalStateException("Cannot add more spots. Total capacity reached: " + totalSpots);
        }
        spots.add(spot);
    }

    public List<ParkingSpot> getSpots() {
        return Collections.unmodifiableList(spots);
    }

    private Optional<ParkingSpot> findSpot(Long spotId) {
        return spots.stream()
                .filter(s -> s.getSpotId().equals(spotId))
                .findFirst();
    }
}
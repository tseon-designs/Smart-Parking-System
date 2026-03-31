package com.smartparking.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

/**
 * Child entity managed by ParkingLot aggregate root.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpot {
    private Long spotId;
    private String location;
    private boolean occupied;
    private BigDecimal pricePerHour;
    private Long parkingLotId;

    public void occupy() {
        this.occupied = true;
    }

    public void release() {
        this.occupied = false;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
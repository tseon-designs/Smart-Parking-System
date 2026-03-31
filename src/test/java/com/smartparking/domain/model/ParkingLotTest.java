package com.smartparking.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {

    @Test
    void shouldCreateParkingLotAndAddSpots() {
        // Arrange
        ParkingLot lot = new ParkingLot(1L, "Test Lot", 100);
        
        // Act & Assert
        assertEquals(1L, lot.getParkingLotId());
        assertEquals("Test Lot", lot.getName());
        assertEquals(100, lot.getTotalSpots());
    }

    @Test
    void shouldBeAbleToInstantiateParkingSpotWithLongIds() {
        // Arrange
        Long lotId = 10L;
        Long spotId = 20L;
        BigDecimal price = BigDecimal.valueOf(10.0);
        
        // Act
        ParkingSpot spot = new ParkingSpot(spotId, "A-1", false, price, lotId);
        
        // Assert
        assertEquals(spotId, spot.getSpotId());
        assertEquals(lotId, spot.getParkingLotId());
        assertFalse(spot.isOccupied());
    }

    @Test
    void shouldHandleSpotOccupation() {
        // Arrange
        ParkingSpot spot = new ParkingSpot(1L, "A-1", false, BigDecimal.valueOf(10.0), 1L);
        
        // Act
        spot.setOccupied(true);
        
        // Assert
        assertTrue(spot.isOccupied());
    }
}

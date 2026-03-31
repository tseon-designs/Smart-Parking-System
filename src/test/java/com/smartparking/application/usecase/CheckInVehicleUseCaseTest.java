package com.smartparking.application.usecase;

import com.smartparking.application.dto.ReservationDto;
import com.smartparking.domain.model.ParkingLot;
import com.smartparking.domain.model.ParkingSpot;
import com.smartparking.domain.model.Reservation;
import com.smartparking.domain.repository.ParkingLotRepository;
import com.smartparking.domain.service.ParkingSpotService;
import com.smartparking.domain.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CheckInVehicleUseCaseTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;
    @Mock
    private ParkingSpotService parkingSpotService;
    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private CheckInVehicleUseCase checkInVehicleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSuccessfullyCheckInVehicle() {
        // Arrange
        Long lotId = 1L;
        Long spotId = 1L;
        String licensePlate = "ABC-123";
        
        ParkingLot lot = new ParkingLot(lotId, "Test Lot", 10);
        ParkingSpot spot = new ParkingSpot(spotId, "A-1", false, BigDecimal.valueOf(15.0), lotId);
        
        Reservation mockSavedReservation = new Reservation(
                100L, spotId, licensePlate, LocalDateTime.now(), null, "ACTIVE"
        );

        when(parkingLotRepository.findById(lotId)).thenReturn(Optional.of(lot));
        when(parkingSpotService.findById(spotId)).thenReturn(spot);
        when(reservationService.createReservation(any())).thenReturn(mockSavedReservation);

        // Act
        ReservationDto result = checkInVehicleUseCase.checkIn(licensePlate, spotId, lotId);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getReservationId());
        assertEquals(licensePlate, result.getVehicleLicensePlate());
        assertEquals("ACTIVE", result.getStatus());
        assertTrue(spot.isOccupied());
    }

    @Test
    void shouldThrowExceptionWhenLotNotFound() {
        // Arrange
        when(parkingLotRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            checkInVehicleUseCase.checkIn("ABC", 1L, 1L)
        );
    }
}

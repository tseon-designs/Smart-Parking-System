package com.smartparking.application.usecase;

import com.smartparking.application.dto.ReservationDto;
import com.smartparking.domain.model.ParkingLot;
import com.smartparking.domain.model.Reservation;
import com.smartparking.domain.model.Vehicle;
import com.smartparking.domain.repository.ParkingLotRepository;
import com.smartparking.domain.service.ParkingSpotService;
import com.smartparking.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CheckInVehicleUseCase {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpotService parkingSpotService;
    private final ReservationService reservationService;

    public ReservationDto checkIn(String licensePlate, Long spotId, Long parkingLotId) {
        // Find the parking lot
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking lot not found"));

        // Verify spot exists and is free
        var spot = parkingSpotService.findById(spotId);
        if (!spot.getParkingLotId().equals(parkingLotId)) {
            throw new IllegalArgumentException("Spot does not belong to this parking lot");
        }
        if (spot.isOccupied()) {
            throw new IllegalStateException("Spot is already occupied");
        }

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setSpotId(spotId);
        reservation.setVehicleLicensePlate(licensePlate);
        reservation.setStartTime(LocalDateTime.now());
        // End time will be set on checkout
        reservation.setStatus("ACTIVE");

        Reservation saved = reservationService.createReservation(reservation);

        // Mark spot as occupied
        spot.setOccupied(true);
        parkingSpotService.save(spot);

        // Convert to DTO
        return new ReservationDto(
                saved.getReservationId(),
                saved.getSpotId(),
                saved.getVehicleLicensePlate(),
                saved.getStartTime(),
                saved.getEndTime(),
                saved.getStatus());
    }
}
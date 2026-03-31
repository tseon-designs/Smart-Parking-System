package com.smartparking.presentation.controller;

import com.smartparking.application.dto.PaymentDto;
import com.smartparking.application.dto.ReservationDto;
import com.smartparking.application.usecase.CheckInVehicleUseCase;
import com.smartparking.application.usecase.CheckOutVehicleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final CheckInVehicleUseCase checkInVehicleUseCase;
    private final CheckOutVehicleUseCase checkOutVehicleUseCase;

    @PostMapping("/checkin")
    public ResponseEntity<ReservationDto> checkIn(@RequestParam String licensePlate,
            @RequestParam Long spotId,
            @RequestParam Long parkingLotId) {
        ReservationDto reservation = checkInVehicleUseCase.checkIn(licensePlate, spotId, parkingLotId);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PostMapping("/checkout")
    public ResponseEntity<PaymentDto> checkOut(@RequestParam Long reservationId,
            @RequestParam String paymentMethod) {
        PaymentDto payment = checkOutVehicleUseCase.checkOut(reservationId, paymentMethod);
        return ResponseEntity.ok(payment);
    }
}
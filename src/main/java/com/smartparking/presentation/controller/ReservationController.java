package com.smartparking.presentation.controller;

import com.smartparking.application.dto.ReservationDto;
import com.smartparking.application.usecase.ReservationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationUseCase reservationUseCase;

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto dto) {
        return new ResponseEntity<>(reservationUseCase.createReservation(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        reservationUseCase.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationUseCase.getReservation(reservationId));
    }

    @GetMapping("/spot/{spotId}/active")
    public ResponseEntity<List<ReservationDto>> getActiveReservationsForSpot(@PathVariable Long spotId) {
        return ResponseEntity.ok(reservationUseCase.getActiveReservationsForSpot(spotId));
    }
}
package com.smartparking.presentation.controller;

import com.smartparking.application.dto.ParkingSpotDto;
import com.smartparking.application.usecase.GetAvailableSpotsUseCase;
import com.smartparking.application.usecase.ParkingSpotUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotUseCase parkingSpotUseCase;

    @GetMapping
    public ResponseEntity<List<ParkingSpotDto>> getAllAvailableSpots() {
        return ResponseEntity.ok(parkingSpotUseCase.getAllAvailableSpots());
    }

    @GetMapping("/{spotId}")
    public ResponseEntity<ParkingSpotDto> getSpot(@PathVariable Long spotId) {
        return ResponseEntity.ok(parkingSpotUseCase.getSpotById(spotId));
    }

    @PostMapping
    public ResponseEntity<ParkingSpotDto> createSpot(@RequestBody ParkingSpotDto dto) {
        return new ResponseEntity<>(parkingSpotUseCase.createSpot(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{spotId}")
    public ResponseEntity<Void> deleteSpot(@PathVariable Long spotId) {
        parkingSpotUseCase.deleteSpot(spotId);
        return ResponseEntity.noContent().build();
    }

    private final GetAvailableSpotsUseCase getAvailableSpotsUseCase;
    // ... other dependencies

    @GetMapping("/available")
    public ResponseEntity<List<ParkingSpotDto>> getAvailableSpots(@RequestParam Long parkingLotId) {
        return ResponseEntity.ok(getAvailableSpotsUseCase.execute(parkingLotId));
    }
}
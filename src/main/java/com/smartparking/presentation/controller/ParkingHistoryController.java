package com.smartparking.presentation.controller;

import com.smartparking.application.dto.ReservationDto;
import com.smartparking.application.usecase.GetParkingHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class ParkingHistoryController {

    private final GetParkingHistoryUseCase getParkingHistoryUseCase;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getHistory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) String licensePlate) {
        return ResponseEntity.ok(getParkingHistoryUseCase.execute(start, end, licensePlate));
    }
}
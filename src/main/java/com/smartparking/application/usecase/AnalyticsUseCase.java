package com.smartparking.application.usecase;

import com.smartparking.domain.service.ParkingSpotService;
import com.smartparking.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AnalyticsUseCase {

    private final ParkingSpotService parkingSpotService;
    private final ReservationService reservationService;

    public long getTotalSpots() {
        return parkingSpotService.countAll();
    }

    public long getOccupiedSpots() {
        return parkingSpotService.countOccupied();
    }

    public Map<LocalDate, Long> getReservationsPerDay(LocalDate start, LocalDate end) {
        return reservationService.countReservationsPerDay(start, end);
    }

    public BigDecimal getTotalRevenue(LocalDate start, LocalDate end) {
        return reservationService.calculateRevenue(start, end);
    }
}
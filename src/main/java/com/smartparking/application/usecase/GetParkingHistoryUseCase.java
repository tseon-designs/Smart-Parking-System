package com.smartparking.application.usecase;

import com.smartparking.application.dto.ReservationDto;
import com.smartparking.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetParkingHistoryUseCase {

    private final ReservationService reservationService;

    public List<ReservationDto> execute(LocalDate start, LocalDate end, String licensePlate) {
        // This is a simplified version – you'd need a method in ReservationService that
        // filters by date and plate
        // For brevity, we'll fetch by date range and filter in memory
        var reservations = reservationService.findByStartTimeBetween(start.atStartOfDay(),
                end.plusDays(1).atStartOfDay());
        if (licensePlate != null && !licensePlate.isEmpty()) {
            reservations = reservations.stream()
                    .filter(r -> r.getVehicleLicensePlate().equals(licensePlate))
                    .collect(Collectors.toList());
        }
        return reservations.stream()
                .map(r -> new ReservationDto(
                        r.getReservationId(),
                        r.getSpotId(),
                        r.getVehicleLicensePlate(),
                        r.getStartTime(),
                        r.getEndTime(),
                        r.getStatus()))
                .collect(Collectors.toList());
    }
}
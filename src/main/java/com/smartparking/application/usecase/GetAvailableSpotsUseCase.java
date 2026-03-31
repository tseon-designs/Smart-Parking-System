package com.smartparking.application.usecase;

import com.smartparking.application.dto.ParkingSpotDto;
import com.smartparking.domain.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAvailableSpotsUseCase {

    private final ParkingSpotService parkingSpotService;

    public List<ParkingSpotDto> execute(Long parkingLotId) {
        return parkingSpotService.findAllAvailable().stream()
                .filter(spot -> spot.getParkingLotId().equals(parkingLotId))
                .map(spot -> new ParkingSpotDto(
                        spot.getSpotId(),
                        spot.getLocation(),
                        spot.isOccupied(),
                        spot.getPricePerHour(),
                        spot.getParkingLotId()))
                .collect(Collectors.toList());
    }
}
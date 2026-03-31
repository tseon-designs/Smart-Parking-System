package com.smartparking.application.usecase;

import com.smartparking.application.dto.ParkingSpotDto;
import com.smartparking.domain.model.ParkingSpot;
import com.smartparking.domain.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ParkingSpotUseCase {

    private final ParkingSpotService parkingSpotService;

    public List<ParkingSpotDto> getAllAvailableSpots() {
        return parkingSpotService.findAllAvailable().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ParkingSpotDto getSpotById(Long spotId) {
        ParkingSpot spot = parkingSpotService.findById(spotId);
        return toDto(spot);
    }

    public ParkingSpotDto createSpot(ParkingSpotDto dto) {
        ParkingSpot spot = toDomain(dto);
        ParkingSpot saved = parkingSpotService.save(spot);
        return toDto(saved);
    }

    public void deleteSpot(Long spotId) {
        parkingSpotService.deleteById(spotId);
    }

    private ParkingSpotDto toDto(ParkingSpot spot) {
        return new ParkingSpotDto(
                spot.getSpotId(),
                spot.getLocation(),
                spot.isOccupied(),
                spot.getPricePerHour(),
                spot.getParkingLotId());
    }

    private ParkingSpot toDomain(ParkingSpotDto dto) {
        return new ParkingSpot(
                dto.getSpotId(),
                dto.getLocation(),
                dto.isOccupied(),
                dto.getPricePerHour(),
                dto.getParkingLotId());
    }
}
package com.smartparking.presentation.mapper;

import com.smartparking.application.dto.ParkingSpotDto;
import com.smartparking.domain.model.ParkingSpot;
import org.springframework.stereotype.Component;

@Component
public class ParkingSpotMapper {

    public ParkingSpotDto toDto(ParkingSpot spot) {
        return new ParkingSpotDto(
                spot.getSpotId(),
                spot.getLocation(),
                spot.isOccupied(),
                spot.getPricePerHour(),
                spot.getParkingLotId());
    }

    public ParkingSpot toDomain(ParkingSpotDto dto) {
        return new ParkingSpot(
                dto.getSpotId(),
                dto.getLocation(),
                dto.isOccupied(),
                dto.getPricePerHour(),
                dto.getParkingLotId());
    }
}
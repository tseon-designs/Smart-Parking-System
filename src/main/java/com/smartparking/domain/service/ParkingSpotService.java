package com.smartparking.domain.service;

import com.smartparking.domain.model.ParkingSpot;
import com.smartparking.domain.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public List<ParkingSpot> findAllAvailable() {
        return parkingSpotRepository.findAllByOccupiedFalse();
    }

    public ParkingSpot findById(Long spotId) {
        return parkingSpotRepository.findById(spotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking spot not found: " + spotId));
    }

    public ParkingSpot save(ParkingSpot spot) {
        return parkingSpotRepository.save(spot);
    }

    public void deleteById(Long spotId) {
        parkingSpotRepository.deleteById(spotId);
    }

    public long countAll() {
        return parkingSpotRepository.count();
    }

    public long countOccupied() {
        return parkingSpotRepository.countByOccupiedTrue();
    }
}
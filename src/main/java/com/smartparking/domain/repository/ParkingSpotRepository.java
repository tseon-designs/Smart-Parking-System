package com.smartparking.domain.repository;

import com.smartparking.domain.model.ParkingSpot;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository {
    List<ParkingSpot> findAllByOccupiedFalse();

    Optional<ParkingSpot> findById(Long spotId);

    ParkingSpot save(ParkingSpot spot);

    void deleteById(Long spotId);

    long count();

    long countByOccupiedTrue();
}
package com.smartparking.domain.repository;

import com.smartparking.domain.model.ParkingLot;

import java.util.Optional;

public interface ParkingLotRepository {
    Optional<ParkingLot> findById(Long parkingLotId);

    ParkingLot save(ParkingLot parkingLot);
    // Optional: find default lot if needed
}
package com.smartparking.domain.repository;

import com.smartparking.domain.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    Optional<Vehicle> findByLicensePlate(String licensePlate);

    Vehicle save(Vehicle vehicle);

    List<Vehicle> findAll();

    void deleteByLicensePlate(String licensePlate);

    boolean existsByLicensePlate(String licensePlate);
}

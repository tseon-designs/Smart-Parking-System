package com.smartparking.infrastructure.persistence.repository;

import com.smartparking.domain.model.Vehicle;
import com.smartparking.domain.repository.VehicleRepository;
import com.smartparking.infrastructure.persistence.entity.VehicleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

interface SpringDataVehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<VehicleEntity> findByLicensePlate(String licensePlate);
    boolean existsByLicensePlate(String licensePlate);
    void deleteByLicensePlate(String licensePlate);
}

@Repository
@RequiredArgsConstructor
public class JpaVehicleRepository implements VehicleRepository {

    private final SpringDataVehicleRepository springRepo;

    @Override
    public Optional<Vehicle> findByLicensePlate(String licensePlate) {
        return springRepo.findByLicensePlate(licensePlate).map(this::toDomain);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleEntity entity = toEntity(vehicle);
        VehicleEntity saved = springRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Vehicle> findAll() {
        return springRepo.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByLicensePlate(String licensePlate) {
        springRepo.deleteByLicensePlate(licensePlate);
    }

    @Override
    public boolean existsByLicensePlate(String licensePlate) {
        return springRepo.existsByLicensePlate(licensePlate);
    }

    private Vehicle toDomain(VehicleEntity entity) {
        return new Vehicle(
                entity.getVehicleId(),
                entity.getLicensePlate(),
                entity.getVehicleType(),
                entity.getOwnerName());
    }

    private VehicleEntity toEntity(Vehicle vehicle) {
        return new VehicleEntity(
                vehicle.getVehicleId(),
                vehicle.getLicensePlate(),
                vehicle.getType(),
                vehicle.getOwnerName());
    }
}

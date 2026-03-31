package com.smartparking.infrastructure.persistence.repository;

import com.smartparking.domain.model.ParkingLot;
import com.smartparking.domain.repository.ParkingLotRepository;
import com.smartparking.infrastructure.persistence.entity.ParkingLotEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

interface SpringDataParkingLotRepository extends JpaRepository<ParkingLotEntity, Long> {
}

@Repository
@RequiredArgsConstructor
public class JpaParkingLotRepository implements ParkingLotRepository {

    private final SpringDataParkingLotRepository springRepo;

    @Override
    public Optional<ParkingLot> findById(Long parkingLotId) {
        return springRepo.findById(parkingLotId).map(this::toDomain);
    }

    @Override
    public ParkingLot save(ParkingLot parkingLot) {
        ParkingLotEntity entity = toEntity(parkingLot);
        ParkingLotEntity saved = springRepo.save(entity);
        return toDomain(saved);
    }

    private ParkingLot toDomain(ParkingLotEntity entity) {
        return new ParkingLot(
                entity.getParkingLotId(),
                entity.getName(),
                entity.getTotalSpots());
    }

    private ParkingLotEntity toEntity(ParkingLot parkingLot) {
        ParkingLotEntity entity = new ParkingLotEntity(
                parkingLot.getParkingLotId(),
                parkingLot.getName(),
                parkingLot.getTotalSpots());
        entity.setActive(true); // Default to active for now
        // Note: Spots are managed within the aggregate, but JPA mapping handles the collection if set
        return entity;
    }
}
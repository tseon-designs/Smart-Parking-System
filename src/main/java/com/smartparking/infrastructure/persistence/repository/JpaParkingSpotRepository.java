package com.smartparking.infrastructure.persistence.repository;

import com.smartparking.domain.model.ParkingSpot;
import com.smartparking.domain.repository.ParkingSpotRepository;
import com.smartparking.infrastructure.persistence.entity.ParkingLotEntity;
import com.smartparking.infrastructure.persistence.entity.ParkingSpotEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

interface SpringDataParkingSpotRepository extends JpaRepository<ParkingSpotEntity, Long> {
    List<ParkingSpotEntity> findAllByOccupiedFalse();

    long countByOccupiedTrue();
}

@Repository
@RequiredArgsConstructor
public class JpaParkingSpotRepository implements ParkingSpotRepository {

    private final SpringDataParkingSpotRepository springRepo;

    @Override
    public List<ParkingSpot> findAllByOccupiedFalse() {
        return springRepo.findAllByOccupiedFalse().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ParkingSpot> findById(Long spotId) {
        return springRepo.findById(spotId).map(this::toDomain);
    }

    @Override
    public ParkingSpot save(ParkingSpot spot) {
        ParkingSpotEntity entity = toEntity(spot);
        ParkingSpotEntity saved = springRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long spotId) {
        springRepo.deleteById(spotId);
    }

    @Override
    public long count() {
        return springRepo.count();
    }

    @Override
    public long countByOccupiedTrue() {
        return springRepo.countByOccupiedTrue();
    }

    private ParkingSpot toDomain(ParkingSpotEntity entity) {
        return new ParkingSpot(
                entity.getSpotId(),
                entity.getLocation(),
                entity.isOccupied(),
                entity.getPricePerHour(),
                entity.getParkingLot() != null ? entity.getParkingLot().getParkingLotId() : null);
    }

    private ParkingSpotEntity toEntity(ParkingSpot spot) {
        ParkingLotEntity lotEntity = null;
        if (spot.getParkingLotId() != null) {
            lotEntity = new ParkingLotEntity();
            lotEntity.setParkingLotId(spot.getParkingLotId());
        }

        return new ParkingSpotEntity(
                spot.getSpotId(),
                spot.getLocation(),
                spot.isOccupied(),
                spot.getPricePerHour(),
                lotEntity);
    }
}
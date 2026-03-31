package com.smartparking.infrastructure.persistence.repository;

import com.smartparking.domain.model.Reservation;
import com.smartparking.domain.repository.ReservationRepository;
import com.smartparking.infrastructure.persistence.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

interface SpringDataReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findBySpotIdAndStatus(Long spotId, String status);

    List<ReservationEntity> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    List<ReservationEntity> findByStartTimeBetweenAndStatus(LocalDateTime start, LocalDateTime end, String status);
}

@Repository
@RequiredArgsConstructor
public class JpaReservationRepository implements ReservationRepository {

    private final SpringDataReservationRepository springRepo;

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return springRepo.findById(reservationId).map(this::toDomain);
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = toEntity(reservation);
        ReservationEntity saved = springRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Reservation> findBySpotIdAndStatus(Long spotId, String status) {
        return springRepo.findBySpotIdAndStatus(spotId, status).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByStartTimeBetween(LocalDateTime start, LocalDateTime end) {
        return springRepo.findByStartTimeBetween(start, end).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByStartTimeBetweenAndStatus(LocalDateTime start, LocalDateTime end, String status) {
        return springRepo.findByStartTimeBetweenAndStatus(start, end, status).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Reservation toDomain(ReservationEntity entity) {
        return new Reservation(
                entity.getReservationId(),
                entity.getSpotId(),
                entity.getVehicleLicensePlate(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getStatus());
    }

    private ReservationEntity toEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getReservationId(),
                reservation.getSpotId(),
                reservation.getVehicleLicensePlate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getStatus());
    }
}
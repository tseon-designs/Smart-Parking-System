package com.smartparking.domain.repository;

import com.smartparking.domain.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Optional<Reservation> findById(Long reservationId);

    Reservation save(Reservation reservation);

    List<Reservation> findBySpotIdAndStatus(Long spotId, String status);

    List<Reservation> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Reservation> findByStartTimeBetweenAndStatus(LocalDateTime start, LocalDateTime end, String status);
}
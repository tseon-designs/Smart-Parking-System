package com.smartparking.domain.service;

import com.smartparking.domain.model.Reservation;
import com.smartparking.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ParkingSpotService parkingSpotService;

    public Reservation createReservation(Reservation reservation) {
        var spot = parkingSpotService.findById(reservation.getSpotId());
        if (spot.isOccupied()) {
            throw new IllegalStateException("Spot is already occupied");
        }
        reservation.setStatus("ACTIVE");
        return reservationRepository.save(reservation);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = findById(reservationId);
        reservation.setStatus("CANCELLED");
        reservationRepository.save(reservation);
    }

    public Reservation findById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + reservationId));
    }

    public List<Reservation> findActiveBySpotId(Long spotId) {
        return reservationRepository.findBySpotIdAndStatus(spotId, "ACTIVE");
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findByStartTimeBetween(LocalDateTime start, LocalDateTime end) {
        return reservationRepository.findByStartTimeBetween(start, end);
    }

    public Map<LocalDate, Long> countReservationsPerDay(LocalDate start, LocalDate end) {
        List<Reservation> reservations = reservationRepository.findByStartTimeBetween(start.atStartOfDay(),
                end.plusDays(1).atStartOfDay());
        return reservations.stream()
                .collect(Collectors.groupingBy(r -> r.getStartTime().toLocalDate(), Collectors.counting()));
    }

    public BigDecimal calculateRevenue(LocalDate start, LocalDate end) {
        List<Reservation> completed = reservationRepository.findByStartTimeBetweenAndStatus(
                start.atStartOfDay(), end.plusDays(1).atStartOfDay(), "COMPLETED");
        return completed.stream()
                .map(r -> {
                    var spot = parkingSpotService.findById(r.getSpotId());
                    long hours = Duration.between(r.getStartTime(), r.getEndTime()).toHours();
                    if (hours == 0)
                        hours = 1;
                    return spot.getPricePerHour().multiply(BigDecimal.valueOf(hours));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
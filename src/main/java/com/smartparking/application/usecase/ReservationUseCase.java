package com.smartparking.application.usecase;

import com.smartparking.application.dto.ReservationDto;
import com.smartparking.domain.model.Reservation;
import com.smartparking.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationUseCase {

    private final ReservationService reservationService;

    public ReservationDto createReservation(ReservationDto dto) {
        Reservation reservation = toDomain(dto);
        Reservation saved = reservationService.createReservation(reservation);
        return toDto(saved);
    }

    public void cancelReservation(Long reservationId) {
        reservationService.cancelReservation(reservationId);
    }

    public ReservationDto getReservation(Long reservationId) {
        Reservation reservation = reservationService.findById(reservationId);
        return toDto(reservation);
    }

    public List<ReservationDto> getActiveReservationsForSpot(Long spotId) {
        return reservationService.findActiveBySpotId(spotId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getReservationId(),
                reservation.getSpotId(),
                reservation.getVehicleLicensePlate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getStatus());
    }

    private Reservation toDomain(ReservationDto dto) {
        return new Reservation(
                dto.getReservationId(),
                dto.getSpotId(),
                dto.getVehicleLicensePlate(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getStatus());
    }
}